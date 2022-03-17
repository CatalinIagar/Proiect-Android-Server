using System.Text;
using System.Threading;
using System.Net;
using System.Net.Sockets;

namespace AndroidServer
{
    class ServerCore : ServerSocket
    {
        private Thread thread = null;
        private bool shouldRun = true;

        public void createServer(int port)
        {
            createSocket(port);
            thread = new Thread(new ThreadStart(run));
            thread.Start();
        }

        private void run()
        {
            while (shouldRun)
            {
                try
                {
                    Socket socket = acceptConnection();
                    if (socket == null)
                        return;

                    Console.WriteLine("Accepted connection from: " + socket.RemoteEndPoint);
                    ClientHandler cl = new ClientHandler(socket, ClientDataStore.Instance.clientCount);
                    cl.initClient();
                    ClientDataStore.Instance.addClient(cl);
                }catch (Exception e)
                {
                    return;
                }
                Thread.Yield();
            }
        }

        public void stopServer()
        {
            closeSocket();
        }

    }
}