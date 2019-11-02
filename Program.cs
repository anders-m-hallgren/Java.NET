using System.Security.Cryptography.X509Certificates;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Hosting;

namespace se.clouds.app.javanet
{
    public class Program
    {
        public static void Main(string[] args)
        {
            CreateHostBuilder(args).Build().Run();
        }

        public static IHostBuilder CreateHostBuilder(string[] args) =>
            Host.CreateDefaultBuilder(args)
                .ConfigureWebHostDefaults(webBuilder =>
                {
                    webBuilder.ConfigureKestrel(options => {
                        //HTTP2 over TLS not working on MAC yet https://github.com/dotnet/corefx/issues/33016
                        /* options.Listen(IPAddress.Any, 5001, option =>
                        {
                            option.Protocols = HttpProtocols.Http2;
                            option.UseHttps("myPrivateServerCert.pfx", "changeit");
                        }); */
                        options.ConfigureHttpsDefaults(option => {
                            option.ServerCertificate=new X509Certificate2("myPrivateServerCert.pfx", "changeit");
                        });
                    });
                    webBuilder.UseStartup<Startup>();
                });
    }
}
