using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;
using System.Net.Sockets;

namespace AndroidServer
{
    class ServerSocket
    {
        private Socket socket;

        protected void createSocket(int port)
        {
            if (socket != null)
                throw new Exception("Socket already exists");

            socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

            try
            {
                socket.Bind(new IPEndPoint(IPAddress.Any, port));
                socket.Listen(10);
            }
            catch(Exception ex)
            {
                throw ex;
            }
        }

        protected Socket acceptConnection()
        {
            try
            {
                return socket.Accept();
            }catch (Exception ex)
            {
                throw ex;
            }
        }

        protected void closeSocket()
        {
            if (socket == null)
                return;
            socket.Close();
            socket = null;
        }
    }
}