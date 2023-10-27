import java.net.*;
import java.io.*;

public class Server{

    ServerSocket Server;            
    Socket Socket;               
    BufferedReader br;          
    PrintWriter out;             


    
    public Server(){
        try {
             Server=new ServerSocket(7777);
             System.out.println("Server is wating for Connection");
             System.out.println("Waiting...............");
             Socket=Server.accept();                     

             br=new BufferedReader(new InputStreamReader(Socket.getInputStream()));  
            out=new PrintWriter(Socket.getOutputStream());   


            StartReading();   

            startWriting();   


            
        } catch (Exception e) {
            
            e.printStackTrace(); 

        }
       

    }
    public void StartReading(){  

          Runnable r1=()->{                   
            System.out.println("reader started...");
        try{
            while(true){
                
                String msg=br.readLine();                      

                if(msg.equals("exit")){        
                    System.out.println("Client terminated the chat");
                    Socket.close();
                    break;
                }
                System.out.println("Client:"+ msg);
            }
        }catch(Exception e){
            System.out.println("Connection closed");
        }
        
    };
          new Thread(r1).start();   

    }

    public void startWriting(){
       
        Runnable r2=()->{
            System.out.println("Writing Started ...");
            try{
            while(true&& !Socket.isClosed()){
         

            BufferedReader br1= new BufferedReader(new InputStreamReader(System.in));   
            String content=br1.readLine();
            out.println(content);
            out.flush();
            if(content.equals("exit")){                
                Socket.close();
                break;
            }


            }
            
        }catch(Exception e){
            e.printStackTrace(); 
        }

            
         

        };
        new Thread(r2).start();;



    }
    public static void main(String[] args) {
        System.out.println("Server is starting .. ");
        
        new Server() ;

    }
}