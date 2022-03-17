using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Runtime.InteropServices;

namespace AndroidServer
{
    class Program
    {
        private static bool exitKeyPressed = false;

        static void Main(string[] args)
        {
            Console.Clear();

            ServerCore sv = new ServerCore();

            try
            {
                sv.createServer(8000);
            }catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }

            Console.CancelKeyPress += new ConsoleCancelEventHandler(keHandler);

            Console.WriteLine("Server is running");
            Console.WriteLine("Press CTRL + C to stop the server!");

            while (!exitKeyPressed)
            {
                Thread.Sleep(100);
            }

            Console.WriteLine("Running cleanup ...");
            Console.WriteLine("Stopping server ...");
            sv.stopServer();
            Console.WriteLine("Cleaning up clients ...");
            ClientDataStore.Instance.stopClients();
            Console.WriteLine("Server is down!");
        }

        private static void keHandler(object? sender, ConsoleCancelEventArgs e)
        {
            Console.WriteLine("The server was interrupted!");
            e.Cancel = true;
            if (e.SpecialKey == ConsoleSpecialKey.ControlC)
                exitKeyPressed = true;
        }
    }
}