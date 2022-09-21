package com.shf.spring.sms.task2;

import com.shf.spring.sms.dto.CheckNode;
import com.shf.spring.sms.task.ASTNode.AST;
import com.shf.spring.sms.task.ASTNode.Analyzer;
import com.shf.spring.sms.task.ASTNode.JSqlParseAnalyzer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InCheck implements CheckSql{
    @Override
    public Set<CheckNode> getNewWeight(List<String> Sql) {

//        CheckNode checkNode = new CheckNode("name1", "name2", 94);
//        Set<CheckNode> inCheckset = new HashSet<>();

//        List<String> list = new ArrayList<>();
//        System.out.println("List :" + JSON.toJSONString(list));
//        System.out.println("String :" + StringUtils.join(list.toArray(), ","));

//        List<String> new_list = new ArrayList<>();
        String[] strs = Sql.toArray(new String[]{});

//        String[] arr = Sql.toArray(new String[Sql.size()]);
        String[] arr = Arrays.toString(strs).toLowerCase().split(":");

//        String[] arr = Sql.split("select");
        if (arr.length - 1 > 0) {
            String sql1 = Sql.toString();
            int lastIndex = sql1.lastIndexOf("in");

            // 1.得到in:前面部分
            String new_list1 = Arrays.toString(strs).substring(0, lastIndex);
            List<String> newlist1 = Arrays.asList(new_list1);

            String sql2 = newlist1.toString();
            int front_lastIndex = sql2.lastIndexOf("in");
            String front_list1 = Arrays.toString(strs).substring(0, front_lastIndex);

            int black_lastIndex1 = front_list1.lastIndexOf(" ");
            String front_list2 = Arrays.toString(strs).substring(0, black_lastIndex1);
            int black_lastIndex2 = front_list2.lastIndexOf(" ");
            String front_list3 = Arrays.toString(strs).substring(black_lastIndex2, black_lastIndex1);

            String front_list4 = getmaintabel(strs, front_list3, black_lastIndex2);


            Analyzer analyzer1 = new JSqlParseAnalyzer();
            AST tree1 = analyzer1.analyze(front_list4);

//            String join1 = String.join(" ", newlist1);
//
//            Analyzer analyzer1 = new JSqlParseAnalyzer();
//            AST tree1 = analyzer1.analyze(join1);

            // 2.得到in:后面部分
            String new_list2 = Arrays.toString(strs).substring(lastIndex + 1);
            int index1 = new_list2.indexOf(":");
            int index2 = new_list2.indexOf("]");
//            List<String> newlist2 = Arrays.asList(new_list2.split(" "));
//
//            String sql3 = newlist2.toString();
//            // 截取字符串
//            int index1 = newlist2.indexOf(" ");
            String behind_list = new_list2.substring(index1 + 1, index2);
//            String[] behindarr = behind_list.toLowerCase().split(":");
//            String behind_list2 = getmaintabel_behind(new_list2, new_list2, index1);


//            String join2 = String.join(" ", behind_list);

            // AST
//            Select select = null;
//            try {
//                select = (Select) CCJSqlParserUtil.parse(behind_list1);
//            } catch (JSQLParserException e) {
//                e.printStackTrace();
//            }
//            SelectBody selectBody = select.getSelectBody();
//            PlainSelect plainSelect = (PlainSelect) selectBody;
//            Table table = (Table)plainSelect.getFromItem();

            Analyzer analyzer2 = new JSqlParseAnalyzer();
            AST tree2 = analyzer2.analyze(behind_list);

            // 表之间的权重关系
            int tableweight = 94;

//            System.out.println("system: 第一个表名为 " + front_list4);
//            System.out.println("system: 第二个表名为 " + behind_list);
////            System.out.println("system: 第二个表名为 " + tree2.getTable());
//
//            System.out.println("system: 两个表之间的关系权重为 " + tableweight);

            // 返回HashSet集合 {"a", "b", 100}
//            String tablename1 = tree1.getTable().get(0);
            String tablename2 = tree2.getTable().get(0);

            String tablename1 = front_list4;
//            String tablename2 = behind_list;

            CheckNode checkNode = new CheckNode(tablename1, tablename2, tableweight);
            Set<CheckNode> inCheckset = new HashSet<>();

            inCheckset.add(checkNode);

            return inCheckset;
        }
        return null;
    }

    private String reverse(String str){
        StringBuffer sb = new StringBuffer(str);
        sb.reverse();
        return sb.toString();
    }

    private String getmaintabel(String[] strs,String list, int index) {
        if(list.contains(".")) {
            int lastIndex = list.lastIndexOf(".") + index;
            String front_list4 = Arrays.toString(strs).substring(index+1, lastIndex);

            return front_list4;
        }
        else{
            return list;
        }
    }

    private String getmaintabel_behind(String strs,String list, int index) {
        if(list.contains(".")) {
            int lastIndex = list.lastIndexOf(".");
            String front_list4 = strs.substring(lastIndex-1, lastIndex);

            return front_list4;
        }
        else{
            return list;
        }
    }

    private String getkeytabel(String newlist1) {


        int index1 = newlist1.indexOf(" ");
        System.out.println("第一个空的索引：" + index1);
        int index2 = newlist1.indexOf(" ", index1 + 1);
        System.out.println("第二个空的索引：" + index2);
        //截取字符串
        String new_list = newlist1.substring(index1 + 1, index2 + 1);
        System.out.println("新的字符串：" + new_list);

        List<String> new_tabel = Arrays.asList(new_list.split("."));
        String join1 = String.join(" ", new_tabel);

        return join1;

    }
}
