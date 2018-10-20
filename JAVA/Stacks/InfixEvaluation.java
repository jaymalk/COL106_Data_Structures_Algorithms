import java.util.Scanner;

// ONLY DECENT
// COULD BE BETTER
// WILL HAVE TO WORK MORE ON IT

public class InfixEvaluation
{
    public static void main(String[] args)
    {
        GenericStack<Double> values = new GenericStack<>();
        GenericStack<String> operations = new GenericStack<>();
        Scanner in  = new Scanner(System.in);
        try
        {
            while(in.hasNext())
            {
                String s = in.next();
                if (s.equals("+"))
                    operations.push(s);
                else if(s.equals("*"))
                    operations.push(s);
                else if (s.equals(")"))
                {
                    String op = operations.pop();
                    if (op.equals("+"))
                    {
                        values.push(values.pop()+values.pop());
                    }
                    else if (op.equals("*"))
                    {
                        values.push(values.pop()*values.pop());
                    }
                }
                else if(!s.equals("("))
                    values.push(Double.parseDouble(s));
            }
            System.out.println("Value = "+values.pop());
        }
        finally
        {
            in.close();
        }
    }
}
