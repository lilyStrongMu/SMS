package com.shf.spring.sms.task2;

import com.shf.spring.sms.dto.CheckNode;
import com.shf.spring.sms.task.ASTNode.AST;
import com.shf.spring.sms.task.ASTNode.Analyzer;
import com.shf.spring.sms.task.ASTNode.JSqlParseAnalyzer;
import com.shf.spring.sms.task.ASTNode.JSqlParseAst;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.statement.select.SelectItem;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Slf4j
public class Layer {
    TreeMap<String, List<String>> map;
    CheckSql join;
    CheckSql re;
    CheckSql inCheck;

    public Layer(TreeMap<String, List<String>> map) {
        this.map = map;
        this.join = new JoinAnalysis();
        this.re = new Relationship();
        this.inCheck = new InCheck();
    }

    public List<CheckNode> getLast() {
        assert map != null;
        Set<CheckNode> checkJoin = new HashSet<>();
        Set<CheckNode> checkRe = new HashSet<>();
        Set<CheckNode> checkIn = new HashSet<>();
        //记录表名和层数的关系
        Map<String, Integer> record = new HashMap<>();
        //记录每一层是否使用函数
        Map<Integer, Boolean> funMap = new HashMap<>();
        Map<CheckNode, Integer> finalMap = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            checkJoin.clear();
            checkRe.clear();
            checkIn.clear();
            String key = entry.getKey();
            List<String> value = entry.getValue();
            String curTable = value.get(1).split(":")[1];
            int curL = Integer.parseInt(key.split("_")[0]);
            assert join != null;
            if (value.get(0).contains("join") || value.get(0).contains("JOIN")) {
                checkJoin = join.getNewWeight(value);
                if (CollectionUtils.isEmpty(checkJoin)){
                    throw new RuntimeException("checkJoin 为空");
                }
            }else if (value.get(0).contains(">") || value.get(0).contains("<") || value.get(0).contains("=")
                    || value.get(0).contains("<=") || value.get(0).contains(">=")) {
                checkRe = re.getNewWeight(value);
                if (CollectionUtils.isEmpty(checkRe)){
                    throw new RuntimeException("checkRe 为空");
                }

            }
            if (value.size() == 4) {
                checkIn = inCheck.getNewWeight(value);
                if (CollectionUtils.isEmpty(checkIn)){
                    throw new RuntimeException("checkIn 为空");
                }
            }
            Map<CheckNode, Integer> temp = new HashMap<>();
            for (CheckNode t : Optional.ofNullable(checkJoin).orElse(Collections.emptySet())) {
                if(temp.containsKey(t) && temp.get(t) < t.getWeight()){
                    temp.put(t, t.getWeight());
                }else if(!temp.containsKey(t)){
                    temp.put(t, t.getWeight());
                }
            }

            for (CheckNode t : Optional.ofNullable(checkRe).orElse(Collections.emptySet())) {
                if(temp.containsKey(t) && temp.get(t) < t.getWeight()){
                    temp.put(t, t.getWeight());
                }else if(!temp.containsKey(t)){
                    temp.put(t, t.getWeight());
                }
            }

//            for (CheckNode t : Optional.ofNullable(checkIn).orElse(Collections.emptySet())) {
//                if(temp.containsKey(t) && temp.get(t) < t.getWeight()){
//                    temp.put(t, t.getWeight());
//                }else if(!temp.containsKey(t)){
//                    temp.put(t, t.getWeight());
//                }
//            }
            //不是第一层，则计算嵌套的权重
            if (!key.contains("1")) {
                for(Map.Entry<CheckNode, Integer> entry2: temp.entrySet()){
                    CheckNode node = entry2.getKey();
                    int w = node.getWeight();
                    String[] curTables = curTable.split(",");
                    for(int j = 0; j < curTables.length; j++){
                        if(curTables[j].trim().equals(node.getName1().trim())){
                            if(record.containsKey(node.getName2())){
                                int preLayer = record.get(node.getName2());
                                for(int i = preLayer; i < curL; i++){
                                    if(funMap.get(i)){
                                        w -= 7;
                                    }else{
                                        w -= 5;
                                    }
                                }
                                node.setWeight(w);
                                temp.put(node, w);
                            }
                        }else if(curTables[j].trim().equals(node.getName2().trim())){
                            if(record.containsKey(node.getName1())){
                                int preLayer = record.get(node.getName1());
                                for(int i = preLayer; i < curL; i++){
                                    if(funMap.get(i)){
                                        w -= 7;
                                    }else{
                                        w -= 5;
                                    }
                                }
                                node.setWeight(w);
                                temp.put(node, w);
                            }
                        }
                    }

                }
            }
            for (CheckNode t : Optional.ofNullable(checkIn).orElse(Collections.emptySet())) {
                if(temp.containsKey(t) && temp.get(t) < t.getWeight()){
                    temp.remove(t);
                    temp.put(t, t.getWeight());
                }else if(!temp.containsKey(t)){
                    temp.put(t, t.getWeight());
                }
            }
           //将当前迭代的权重放入总的权重finalMap中
            for(Map.Entry<CheckNode, Integer> t : temp.entrySet()){
                CheckNode node = t.getKey();
                int w = t.getValue();
                if(finalMap.containsKey(node)){
                    if(finalMap.get(node) < w){
                        finalMap.put(node, w);
                    }
                }else{
                    finalMap.put(node, w);
                }
            }
            String[] curTableNames = value.get(1).split(":")[1].split(",");
            for(String cName : curTableNames){
                record.put(cName, curL);
            }
            boolean flag = containsFunction(value.get(0).split(":")[1]);
            if(funMap.containsKey(curL)){
                if(flag){
                    funMap.put(curL, true);
                }
            }else{
                funMap.put(curL, flag);
            }
        }
        return new ArrayList<>(finalMap.keySet());
    }

    /*
    计算当前sql的select字段是否使用了函数
     */
    public boolean containsFunction(String sql){
        if(sql.contains("where")){
            sql = sql.split("where")[0];
        }else if(sql.contains("WHERE")){
            sql = sql.split("WHERE")[0];
        }
        Analyzer analyzer = new JSqlParseAnalyzer();
        AST tree = analyzer.analyze(sql);
        List<SelectItem> selectItems = tree.getSelects();
        for(SelectItem item : selectItems){
            if(selectItems.toString().contains("(")){
                return true;
            }
        }
        return false;
    }
}
