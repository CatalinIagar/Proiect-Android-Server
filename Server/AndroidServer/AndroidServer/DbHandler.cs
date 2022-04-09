using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AndroidServer
{
    internal class DbHandler
    {
        public static string messageProcessing(string[] arrMsg)
        {
            

            if (arrMsg[0].Equals(Mesaje.sSignupReq))
            {
                Console.WriteLine(proccesSignupReq(arrMsg)); 
                return proccesSignupReq(arrMsg);
            }
            else
            {
                return Mesaje.cReqErr;
            }
            
        }

        private static string proccesSignupReq(string[] arrMsg)
        {
            foreach(String s in arrMsg) Console.WriteLine(s);

            if (checkUsername(arrMsg[1])) return Mesaje.cSignupUserErr;
            if (checkEmail(arrMsg[3])) return Mesaje.cSignupEmailErr;

            try
            {
                using (UsersDbContext context = new UsersDbContext())
                {
                    Users user = new Users();
                    user.Username = arrMsg[1];
                    user.Password = arrMsg[2].GetHashCode();
                    user.Email = arrMsg[3];
                    user.CNP = arrMsg[4];
                    user.PhoneNumber = arrMsg[5];
                    user.Balance = 0;

                    context.UsersList.Add(user);
                    context.SaveChanges();
                }
            }catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            

            return Mesaje.cSignupOk;
        }

        private static bool checkEmail(string v)
        {
            using (UsersDbContext context = new UsersDbContext())
            {
                int query = (from u in context.UsersList
                             where u.Email == v
                             select u).Count();

                if (query > 0) return true;
                return false;
            }
        }

        private static bool checkUsername(string v)
        {
            using (UsersDbContext context = new UsersDbContext())
            {
                int query = (from u in context.UsersList
                             where u.Username == v
                             select u).Count();

                if (query > 0) return true;
                return false;
            }
        }
    }
}
