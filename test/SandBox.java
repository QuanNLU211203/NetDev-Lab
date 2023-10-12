public class SandBox {
    public static void main(String[] args) throws Exception{
        while (true){
            int data = System.in.read();
            if(data != -1){
                System.out.println(data);
                char c = (char) data;
                if(c == '\n'){
                    System.out.println("Enter");
                }
                else{
                    System.out.println(c);
                }
            }
        }
    }
}
