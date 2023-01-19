import java.util.*;

public class RewriteArithmeticStatement {

    void rewrite(String aS){
        StringBuilder sb = new StringBuilder();
        Stack<String> st = new Stack<>();
        List<String> l = new ArrayList<>();
        int i = 1;
        for(Character ch : aS.toCharArray())
        {
            if(ch != ')' && ch != ';')
                st.add(ch.toString());
            else if(ch == ';')
            {
                Iterator it = st.iterator();
                while(it.hasNext())
                    sb.append(st.pop());
            }
            else{
                StringBuilder sb1 = new StringBuilder();
                while(!st.peek().equals("("))
                {
                    sb1.append(st.pop());
                }
                st.pop();
                sb1.append("="+i+"z");
                l.add(sb1.reverse().toString());
                st.add(i+"z");
                i++;
            }
        }

        System.out.println(sb.reverse().toString());
        for(String s : l)
            System.out.println(s);
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String aS = sc.next();
        RewriteArithmeticStatement re = new RewriteArithmeticStatement();
        re.rewrite(aS);
    }

}
