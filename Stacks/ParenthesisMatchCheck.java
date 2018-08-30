import java.util.Scanner;

public class ParenthesisMatchCheck
{
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int i = 0, check=0;
        try
        {
            GenericStack<String> stack = new GenericStack<>();
            while(in.hasNext())
            {
                String s = in.next();
                i++;
                if(s.equals("("))
                    stack.push(s);
                else
                    stack.pop();
                if(stack.isEmpty())
                    check = i;
            }
            if(stack.isEmpty())
                System.out.println("Parenthesis Matched correctly");
            else
            {
                System.out.println("Parenthesis Missing\nProblem for the Parenthesis no. "+check+"\nMore ')' needed.");
            }
        }
        catch(NullPointerException e)
        {
            System.out.println("Parenthesis Not Matched Correctly. Error in Parenthesis no. "+i);
        }
        finally
        {
            in.close();
        }
    }
}
