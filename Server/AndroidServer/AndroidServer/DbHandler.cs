using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net;
using System.Net.Mail;
using System.Net.Mime;

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
            else if (arrMsg[0].Equals(Mesaje.sAddBalance))
            {
                return proccesAddBalanceReq(arrMsg);
            }
            else if(arrMsg[0].Equals(Mesaje.sTransfer))
            {
                return proccesTransferReq(arrMsg);
            }
            else if (arrMsg[0].Equals(Mesaje.sChangePass))
            {
                return proccesChangePassReq(arrMsg);
            }
            else if (arrMsg[0].Equals(Mesaje.sChangeEmail))
            {
                return proccesChangeEmailReq(arrMsg);
            }
            else if (arrMsg[0].Equals(Mesaje.sForgotPass))
            {
                return proccesForgotPassReq(arrMsg);
            }
            else
            {
                return Mesaje.cReqErr;
            }

        }

        private static string proccesForgotPassReq(string[] arrMsg)
        {
            try
            {
                using (UsersDbContext context = new UsersDbContext())
                {
                    String tempUsername = arrMsg[1];

                    int count = (from u in context.UsersList
                                 where u.Username == tempUsername
                                 select u).Count();

                    if (count > 0)
                    {
                        Users user = (from u in context.UsersList
                                      where u.Username == tempUsername
                                      select u).First();

                        var smtpClient = new SmtpClient("smtp.gmail.com")
                        {
                            Port = 587,
                            Credentials = new NetworkCredential("cataliniagar.testing@gmail.com", "Test1234@"),
                            EnableSsl = true,
                        };

                        smtpClient.Send("cataliniagar.testing@gmail.com", user.Email, "Your Password", "The password for user " + user.Username + " is nothing (I forgot i hashed the password and can't get it back)");

                        return Mesaje.cForgotPassOk + " ";
                    }
                    return Mesaje.cForgotPassUserErr + " ";
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                return Mesaje.cLoginErr + " ";
            }

        }

        private static string proccesChangeEmailReq(string[] arrMsg)
        {
            try
            {
                using (UsersDbContext context = new UsersDbContext())
                {
                    String tempUsername = arrMsg[1];
                    String oldEmail = arrMsg[2];
                    String newEmail = arrMsg[3];

                    int count = (from u in context.UsersList
                                 where u.Username == tempUsername
                                 select u).Count();

                    if (count > 0)
                    {
                        Users user = (from u in context.UsersList
                                      where u.Username == tempUsername
                                      select u).First();

                        if (user.Email == oldEmail)
                        {
                            user.Email = newEmail;

                            context.SaveChanges();
                            return Mesaje.cChangeEmailOk + " " + newEmail + " ";
                        }
                        return Mesaje.cChangeEmailOldErr + " ";
                    }
                    return Mesaje.cChangeEmailErr + " ";
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                return Mesaje.cLoginErr + " ";
            }

        }

        private static string proccesChangePassReq(string[] arrMsg)
        {
            try
            {
                using (UsersDbContext context = new UsersDbContext())
                {
                    String tempUsername = arrMsg[1];
                    int oldPass = arrMsg[2].GetStableHashCode();
                    int newPass = arrMsg[3].GetStableHashCode();
                    int tempPassword = arrMsg[2].GetStableHashCode();

                    int count = (from u in context.UsersList
                                 where u.Username == tempUsername
                                 select u).Count();

                    if (count > 0)
                    {
                        Users user = (from u in context.UsersList
                                      where u.Username == tempUsername
                                      select u).First();

                        if (user.Password == oldPass)
                        {
                            user.Password = newPass;

                            context.SaveChanges();
                            return Mesaje.cChangePassOk + " ";
                        }
                        else
                        {
                            return Mesaje.cChangePassOldErr + " ";
                        }
                    }
                    return Mesaje.cChangePassErr + " ";
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                return Mesaje.cLoginErr + " ";
            }

        }

        private static string proccesTransferReq(string[] arrMsg)
        {
            try
            {
                using (UsersDbContext context = new UsersDbContext())
                {
                    String tempUsername = arrMsg[1];
                    String tempUsernameT = arrMsg[2];
                    int tempPassword = arrMsg[3].GetStableHashCode();

                    int count = (from u in context.UsersList
                                 where u.Username == tempUsername && u.Password == tempPassword
                                 select u).Count();

                    if (count > 0)
                    {
                        int count2 = (from u in context.UsersList
                                       where u.Username == tempUsernameT
                                       select u).Count();

                        if (count2 > 0)
                        {
                            Users user = (from u in context.UsersList
                                          where u.Username == tempUsername
                                          select u).First();

                            Users userT = (from u in context.UsersList
                                           where u.Username == tempUsernameT
                                           select u).First();

                            int currentBalance = user.Balance;
                            int currentBalanceT = userT.Balance;

                            currentBalance -= int.Parse(arrMsg[4]);
                            currentBalanceT += int.Parse(arrMsg[4]);

                            user.Balance = currentBalance;
                            userT.Balance = currentBalanceT;

                            context.SaveChanges();

                            return Mesaje.cTransferOk + " " + user.Balance.ToString() + " ";
                        }
                        return Mesaje.cTransferUserErr + " ";                       
                    }
                    return Mesaje.cTransferErr + " ";
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                return Mesaje.cLoginErr + " ";
            }

        }

        private static string proccesAddBalanceReq(string[] arrMsg)
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

                        int currentBalance = user.Balance;

                        currentBalance += int.Parse(arrMsg[3]);

                        user.Balance = currentBalance;

                        context.SaveChanges();

                        return Mesaje.cAddBalanceOk + " " + user.Balance.ToString() + " ";
                    }
                    return Mesaje.cAddBalanceErr + " ";
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                return Mesaje.cLoginErr + " ";
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
                    return Mesaje.cLoginErr + " ";
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                return Mesaje.cLoginErr + " ";
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
            

            return Mesaje.cSignupOk + " ";
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
