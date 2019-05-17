/*
https://www.youtube.com/watch?v=u_iU4nJAFac
https://www.youtube.com/watch?v=oDi8NCX0VyQ
http://camposha.info/course/android-mysql/lesson/android-php-mysql-save-http-post-httpurlconnection/ başka bir eğitim
imdi android kımında bir adet buton ve bir adet editText’imiz var editText’den girdiğim değer veritabanına kaydoluyor ve eş zamalı olarak da veritabanı içerisinde ki tüm kayıtlar Toast ile ekranda gösteriliyor böyle de mükemmel mantıklı bir program tabi burada bir de volley kütüphanesini eklememiz gerekiyor onuda en kolay yöntem ile grandle kısmına
*/
//"compile 'com.mcxiaoke.volley:library:1.0.+'" bunu yapıştırırsak volley'in en güncel kütüphanesine de sahip olmuş oluruz.

public class MainActivity extends AppCompatActivity {
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buton=(Button)findViewById(R.id.button);
        final EditText text=(EditText)findViewById(R.id.editText);
        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gonder(text.getText().toString());
          }
        });
        }
  void gonder(final String mesaj)
    {
        String url="http://ibrahimozcelik.net/kaydetDeneme/kaydet.php";//php dosyamızın olduğu adres
        final StringRequest istek=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {//bağlantı için kullandığımız sınıf
            @Override
            public void onResponse(String response) {//geri dönen cevap json olarak
                 try {
                    JSONArray geriDonenVeri=null;//json dizisi oluşturuyoruz
                    JSONObject jsonObject=null;//dizinin içinde ki objelere ulaşmak için json obje oluşturuyoruz
                    
                    jsonObject=new JSONObject(response);//bize dönen cevabı obje olarak alıyoruz
                    geriDonenVeri=jsonObject.getJSONArray("isimler");//ve php sayfasında belirttiğimiz isim ile bunun altında ki verilere erişiyoruz
                    ArrayList gelen=new ArrayList();//bu isimler bir nevi anahtar değer gibi düşünülebilir
                   
                    for (int i=0;i<geriDonenVeri.length();i++)//json dizisinin boyutu kadar yani veritabanı uzunluğu kadar dönüyoruz
                    {
                         gelen.add(geriDonenVeri.getString(i));//geri dönen değerleri arraylist ile saklıyoruz
                         Toast.makeText(MainActivity.this,gelen.get(i).toString(),Toast.LENGTH_LONG).show();//ve aynı zamanda ekranda gösteriyoruz
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
        new Response.ErrorListener() {//herhangi bir hata varsa onaylanmadı şeklinde uyarı mesajı verdiriyoruz
                    @Override
                    public void onErrorResponse(VolleyError error) {//hata olursa geri dönen hata mesajı
                        Toast.makeText(MainActivity.this,"istek onaylanmadı",Toast.LENGTH_SHORT).show();
                    }
                }){
            protected Map getParams()//bu metodu ovveride ediyoruz ve sunucuya göndereceğimiz verileri buradan gönderiyoruz
            {//metod ismi aynen bu şekilde olmalı
                Map<String,String> params=new HashMap<String, String>();//Map yapısında göndereceğimiz değerin anahtar ve değer kısmı var
                params.put("metin",mesaj);//bu değerleri belirleyip bu satırda ki gibi yolluyoruz php sayfa da bu veriye misal "metin" anahtar
                 
                return params;//değeri ile erişeceğiz bu değerin içeriğine
            }
                 };
        RequestQueue kuyruk=Volley.newRequestQueue(getApplicationContext());//burada artık istekler kuyruğa aktarılıyor ve işlemler gerçekleştiriliyor.
        kuyruk.add(istek);
 
    }
}
/*
Bu kısımda dikkat etmemiz gereken durumlar JSON dizisini doğru parçalayabilmek ben bu şekilde yaptım şuan ama farklı yöntemlerde mevcut, durumdan duruma göre değişir tahmin edeceğiniz üzere. Veri gönderirken Map getParams metodu ile gönderiyoruz.
*/