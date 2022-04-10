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
                return proccesSignupReq(arrMsg);
            }
            else if (arrMsg[0].Equals(Mesaje.sLoginReq))
            {
                return proccesLoginReq(arrMsg);
            }
            else
            {
                return Mesaje.cReqErr;
            }
            
        }

        private static string proccesLoginReq(string[] arrMsg)
        {
            try
            {
                using (UsersDbContext context = new UsersDbContext())
                {
                    String tempUsername = arrMsg[1];
                    int tempPassword = arrMsg[2].GetStableHashCode();   

                    int count = (from u in context.UsersList
                                 where u.Username == tempUsername && u.Password == tempPassword
                                 select u).Count();

                    if (count > 0)
                    {
                        Users user = (from u in context.UsersList
                                      where u.Username == tempUsername
                                      select u).First();
                        return Mesaje.cLoginOk + " " + user.Username + " " + user.Email + " " + user.CNP + " " + user.PhoneNumber + " " + user.Balance;
                    }
                    Console.WriteLine(arrMsg[1] + " " + arrMsg[2]);
                    return Mesaje.cLoginErr;
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                return Mesaje.cLoginErr;
            }
            
        }

        private static string proccesSignupReq(string[] arrMsg)
        {
            if (checkUsername(arrMsg[1])) return Mesaje.cSignupUserErr;
            if (checkEmail(arrMsg[3])) return Mesaje.cSignupEmailErr;

            try
            {
                using (UsersDbContext context = new UsersDbContext())
                {
                    Users user = new Users();
                    user.Username = arrMsg[1];
                    user.Password = arrMsg[2].GetStableHashCode();
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
