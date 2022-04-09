using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;
using System.Net.Sockets;
using System.Threading;

namespace AndroidServer
{
    class ClientHandler
    {
        private Socket socket = null;
        private int index = -1;
        private Thread thread = null;
        private bool shouldRun = true;
        private bool isRunning = true;
        
        public ClientHandler(Socket socket, int id)
        {
            this.socket = socket;
            this.index = id;
        }

        public void initClient()
        {
            if (thread != null)
                return;

            thread = new Thread(new ThreadStart(run));
            thread.Start();
        }

        public void stopClient()
        {
            if(thread == null)
                return ;
            
            socket.Close();
            shouldRun = false;
        }

        public bool SocketConencted(Socket s)
        {
            bool part1 = s.Poll(10000, SelectMode.SelectRead);
            bool part2 = (s.Available == 0);
            if (part1 && part2)
                return false;
            else
                return true;
        }

        private void run()
        {
            while (shouldRun)
            {
                byte[] rawMsg = new byte[1000];

                try
                {
                    int bCount = socket.Receive(rawMsg);
                    String msg = Encoding.UTF8.GetString(rawMsg);
                    if (bCount > 0)
                        Console.WriteLine("Client " + index + ": " + msg);
                    handleMsg(msg);
                }catch (Exception e)
                {
                    return;
                }
                Thread.Sleep(1);
            }
            isRunning = false;
        }

        private void handleMsg(String msg)
        {
            Console.WriteLine(msg);
            char[] sep = {' '};
            String[] arrMsg = msg.Split(sep);

            String raspuns = DbHandler.messageProcessing(arrMsg);
            sendResponseLogin(raspuns);
        }

        private void sendResponseLogin(String msg)
        {
            String raspuns = "";
            raspuns = raspuns.Insert(0, msg);
            Console.WriteLine(raspuns);
            byte[] bytesMsgRaspuns = Encoding.ASCII.GetBytes(raspuns);
            socket.Send(bytesMsgRaspuns);
        }
    }
}