import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.*;
import java.net.*;
import  java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Client extends JFrame {

    Socket Socket;
    BufferedReader br;           
    PrintWriter out;  
    
    private JLabel heading = new JLabel("Client Area");
    private JTextArea messageArea=new JTextArea();
    private JTextField messageInput=new JTextField();
    private Font font =new Font("Roboto ",Font.PLAIN,20);



    public Client(){                                                     
        try {
             System.out.println("Sending request to server");

            Socket=new Socket("127.0.0.1",7777);
            System.out.println("connection done");


             br=new BufferedReader(new InputStreamReader(Socket.getInputStream())); 
            out=new PrintWriter(Socket.getOutputStream());   

            CreateGUI();
            handleEvents();

           StartReading();   

           // startWriting();  
            
            


     } catch (Exception e) {
            
        }

    }
    private void handleEvents() {
        messageInput.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
        
                if(e.getKeyCode() == 10){
                    String contentToSend=messageInput.getText();
                    messageArea.append("Me:"+"contentTosend+"\n");
                    out.println(contentToSend);
                    out.flush();
                    messageInput.setText("");
                    messageInput.requestFocus();
                }
            }
            
        });

        
    }
    private void CreateGUI(){
        this.setTitle("Client Message[END]");
        this.setSize(500,500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        heading.setFont(font);
        messageArea.setFont(font);
        messageInput.setFont(font);
        heading.setIcon(new ImageIcon("clongo.png.jpg"));

        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        messageInput.setHorizontalAlignment(SwingConstants.CENTER);

        this.setLayout(new BorderLayout());

        this.add(heading,BorderLayout.NORTH);
        JScrollPane jScrollPane=new JScrollPane(messageArea);
        this.add(jScrollPane,BorderLayout.CENTER);
        this.add(messageInput,BorderLayout.SOUTH);
        this.setVisible(true);
    }
        public void StartReading(){  
        
                  Runnable r1=()->{                   
                    System.out.println("reader started...");
                  try{
                    while(true){
                        
                        
                        String msg=br.readLine();                    
        
                        if(msg.equals("exiexitt")){        
                            System.out.println("Server terminated the chat");
                            JOptionPane.showMessageDialog(this, "Server Terminated the chat" );
                            messageInput.setEnabled(false);
                            Socket.close();
                        
                            break;
                        }
                       // System.out.println("Server :"+ msg);
                       messageArea.append("Server :"+ msg+"\n");
                    }
                }catch(Exception e)
                    {
                        System.out.println("Connection  is closed");
                    }
                
        
        
                  };
                  new Thread(r1).start();    //to Start a thread;
        
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
        System.out.println("this is client....");
        new Client();
        
    }
    
}
