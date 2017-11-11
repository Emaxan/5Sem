using System.Data.Entity.ModelConfiguration;
using OOP.Models;

namespace OOP.DataAccess.Context.Configurations
{
    public class UserConfiguration : EntityTypeConfiguration<User>
    {
        public UserConfiguration()
        {
            HasKey(u => u.Id);
            Property(u => u.Name).IsRequired();
            Property(u => u.Email).IsRequired();
            Property(u => u.Password).IsRequired();
        }
    }
}
