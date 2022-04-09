using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AndroidServer
{
    public class Users
    {
        [Key]
        [Required]
        public int Id { get; set; }

        [Required]
        [MinLength(8)]
        [MaxLength(20)]
        public string? Username { get; set; }

        [Required]
        public int Password { get; set; }

        [Required]
        public string? Email { get; set; }

        [Required]
        public string? CNP { get; set; }

        [Required]
        public string? PhoneNumber { get; set; }

        [Required]
        public int Balance { get; set; } 
    }

    public class UsersDbContext : DbContext
    {
        public DbSet<Users> UsersList { get; set; }
    }
}
