using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


namespace AndroidServer
{
    class ClientDataStore
    {
        private List<ClientHandler> clients = new List<ClientHandler> ();
        private static ClientDataStore instance = new ClientDataStore ();

        private ClientDataStore()
        {

        }

        public static ClientDataStore Instance
        {
            get
            {
                return instance;
            }
        }

        public void addClient(ClientHandler cl)
        {
            lock (clients)
            {
                clients.Add(cl);
            }
        }

        public void stopClients()
        {
            lock (clients)
            {
                foreach (ClientHandler cl in clients)
                    cl.stopClient();
            }
        }

        public int clientCount
        {
            get { return clients.Count; }
        }
    }
}