using System.Data.Entity;
using OOP.DataAccess.Context.Configurations;
using OOP.Models;

namespace OOP.DataAccess.Context
{
    public class OOPDbContext: DbContext
    {
        public DbSet<User> Users { get; set; }

        public DbSet<Pizza> Pizzas { get; set; }

        public OOPDbContext() : base("OOP")
        {
            Configuration.LazyLoadingEnabled = false;
        }

        public OOPDbContext(string connectionString) : base(connectionString)
        {
            Configuration.LazyLoadingEnabled = false;
        }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Configurations.Add(new UserConfiguration());
            modelBuilder.Configurations.Add(new PizzaConfiguration());
            base.OnModelCreating(modelBuilder);
        }

        public new IDbSet<TEntity> Set<TEntity>() where TEntity : class
        {
            return base.Set<TEntity>();
        }
    }
}
