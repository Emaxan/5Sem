using System.Web.Mvc;

namespace OOP.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult Index()
        {
            return View();
        }

        public ActionResult About()
        {
            ViewBag.Message = "Pizza store :-)";

            return View();
        }

        public ActionResult Contact()
        {
            ViewBag.Message = "My contacts.";

            return View();
        }
    }
}