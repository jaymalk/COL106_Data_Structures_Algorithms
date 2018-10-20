
public class ParenthesisMatchCheck
{
    public static void main(String[] args) {
        String expression = args[0];
        int i = 0;
        final String lefts = "({[", rights = ")}]";
        try
        {
            GenericStack<Character> stack = new GenericStack<>();
            for(Character c : expression.toCharArray()) {
                i++;
                if(lefts.indexOf(c) != -1) {
                    stack.push(c);
                }
                else if (rights.indexOf(c) != -1)
                    if (lefts.indexOf(stack.pop()) != rights.indexOf(c))
                        throw new Exception();
            }
            if(stack.isEmpty())
                System.out.println("Correctly Matched!");
            else
                throw new Exception();
        }
        catch(Exception e)
        {
            System.out.println("Parenthesis not matched correctly. Error in postion no. "+i);
        }
    }
}
