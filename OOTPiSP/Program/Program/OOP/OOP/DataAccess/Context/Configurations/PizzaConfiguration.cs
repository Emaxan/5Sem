using System.Data.Entity.ModelConfiguration;
using OOP.Models;

namespace OOP.DataAccess.Context.Configurations
{
    public class PizzaConfiguration : EntityTypeConfiguration<Pizza>
    {
        public PizzaConfiguration()
        {
            HasKey(p => p.Id);
            Property(p => p.Cost).IsRequired();
            Property(p => p.Name).IsRequired();
            Property(p => p.Description).IsRequired();
        }
    }
}
