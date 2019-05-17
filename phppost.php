<?php 
if($_POST)//kodları sayfada gizler { 
//veritabanına yazılacak bilgiler 
$ad = stripslashes($_POST["metin"]);//android uygulamadan aldığımız post mesajı 
//veritanı bağlantısı 
try{
$baglanti = new PDO("mysql:host=localhost;dbname=ibrahim;charset=utf8", "ibrahim", "ibrahim"); //veritabanı adı, veritabanı kullanıcı adı, veritabanı şifresi 
$baglanti -> setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);//hata mesajlarını görmek için
 
}
catch(PDOException $e)
{
 print $e -> getMessage();
}
//veritabanına ekleme işlemleri
 
$gir = $baglanti -> prepare("INSERT INTO metin SET ad = :a");//burada standart insert ile ekleme işlemi yapıyoruz
$sqlGit = $gir -> execute(array("a" => $ad));//execute ile çalıştırıyoruz ad değeri veritabanında ki alan adı
 
 
$query = $baglanti -> query("SELECT * FROM metin");//burada geri döndereceğimiz değerler için veritabanında ki tüm değerleri alıyoruz
  
 $JSON["isimler"]=array();//değerleri JSON adlı dizimiz "isimler" anahtar değeri ile aktarıyoruz.
 if($query -> rowCount())//veritabında kayıt varsa
 {
 foreach($query as $row)//veritabanında ki kayıt sayısınca dönüyoruz
 {
  
 $JSON["isimler"][]=$row["ad"];//elde edilen kayıtları dizimize aktarıyoruz
  
 }
 }
  
 else
 {
 echo "kayıt yok";
 }
echo json_encode($JSON);//en son elimizde ki diziyi bu şekilde JSON'a çeviriyoruz
}

/*

Buraya kadar bizi veritabanı ile konuşturacak php sayfamızı hazırladık. Burada PDO ile veritabanı bağlantısı yaptık standart mysql bağlantısı yapmadık çünkü artık o bağlantı çeşidinin miladını doldurduğu belirtiliyor ve de PDO sadece mysql değil daha bir çok veritabanı bağlantısını sağlayabiliyor gibi bir çok özelliği var. Bura da hem veriyi kaydediyoruz hem de bize veritabanında ki kayıtları JSON olarak geri döndermesini sağlıyoruz. Burada işin güzel tarafı ben buraya istediğim platformdan veri gönderebilirim ve gönderebildiğim takdirde alınan veri veritabanı işlemlerine tabi tutulabilir.
*/

?>
