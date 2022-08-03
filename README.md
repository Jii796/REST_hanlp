# REST_hanlp
### 模型的生成以及使用
REST_hanlp是一个RESTful风格的nlp框架，这里提供了项目的开源地址以及打包好的jar文件地址。
#### 项目工程介绍
- 该项目的源码在仓库[https://github.com/Jii796/Spring_hanlp.git](https://github.com/Jii796/Spring_hanlp.git)下，其中该仓库包含两个项目文件分别为Hanlp-1.8.3和REST_hanlp。其中前一个项目是Spring MVC框架和nlp框架的融合（是一个WEB项目），后一个项目是RESTful风格的nlp框架（是一个maven项目）。
- JDK版本要求：1.8（注意：其他版本的JDK也可能可以执行，但是会出现一些警告错误）
- REST_hanlp项目介绍：

1. data文件夹。该文件夹下包含dictionary和model两个文件夹，这是自然语言处理的时候需要用到的模型文件和字典文件，在初次运行项目的时候会直接下载相应的模型文件。另外在下载该项目并本地运行之后，data文件夹下会多出来一个test/hanlp_rest_data-master文件夹，里面存放的是文本分类需要用到的训练数据，当然也可以直接本地下载训练数据然后解压放到项目文件夹下：[点击下载](https://codeload.github.com/Jii796/hanlp_rest_data/zip/refs/heads/master)，其中包含六种类型的训练数据。

2. src文件夹。该文件夹下包含main和test两个文件夹，其中模型的具体实现在main文件夹下，包括文本分类的实现（TextClassify.java）、接口的实现(Controller.java)等。而test文件夹下则是java编写的模拟客户端来测试相应的接口。包括四个文件：JavaNetURLRESTFulClientGet.java、JavaNetURLRESTFulClientPost.java、demoTemplateGet.java和demoTemplatePost.java。分别是java自带的HttpURLConnection编写的模拟GET和POST请求和restTemplate编写的模拟GET和POST请求。其中使用HttpURLConnection的时候需要调整Controller.java文件中的参数的读入格式和结果的返回格式（需要全部转换为UTF-8编码），而使用restTemplate则不需要修改Controller.java文件。

3. Controller.java文件。在该文件中主要实现了三个接口，两个是文本分类的接口（一个GET方法，一个POST方法），还有一个文本推荐的接口（GET方法）：
```
textClassifyControllerPost(HttpServletRequest request,String text)、
TextClassifyControllerGet(HttpServletRequest request, @PathVariable(value = "text") String text)
TextSuggestController(@PathVariable(value="keyword") String keyword)。
```
其中在文本分类的两个接口中除了获得参数文本之外，还将请求报文的内容在控制台显示出来，而文本推荐的接口（它所需要的参数是一个关键字）虽然已经实现，但是在内网服务器上运行的时候是将这一部分注释掉了。
- 在下载开源项目之后，右键执行TextClassify.java文件，下载相应的数据，之后再执行Controller.java文件，这时候可以通过test文件夹下的几个文件进行测试。 **注意：文件的编码格式一定要是UTF-8格式，否则会很容易出现中文乱码的问题。** 

#### 模型的直接使用
- 除了开源的项目源码之外，在这里还提供了已经打包成功的jar文件，下载地址为：[https://github.com/Jii796/REST_hanlp.git](https://github.com/Jii796/REST_hanlp.git)
- 项目介绍：

1. jar文件。该仓库下面包含了三个jar文件，分别是hanlp-1.8.3-sources.jar、hanlp-1.8.3.jar和hanlp-1.8.3.jar.original。其中将jar文件本地运行的时候，需要将这三个文件全部都下载下来，否则会导致部分数据下载缺失。初次运行通过以下命令`java -jar hanlp-1.8.3.jar`，在第一次运行的时候会下载相应的字典文件和模型文件和训练数据，这个会花费大约五到十分钟左右的时间。

2. 模拟客户端（java）。该客户端是通过RESTTemplate编写，是一个maven项目，其中主要代码在src/test/java文件夹下。包含ClientGet.java和ClientPost.java两个文件，分别模拟GET请求和POST请求，注意，在本地模拟的时候需要将相应的URL地址修改为本地地址。

#### 接口的调用
其中文本分类的接口可以通过GET方式调用，也可以通过POST方式调用，但是建议通过POST方式，调用代码如下所示：

```
public class ClientPost {

    public static final String[] type= {"first-type","second-type","third-type","forth-type","fifth-type","sixth-type"};

    public static final String[] type_prossibility= {"first-type-prossibility","second-type-prossibility","third-type-prossibility",
            "forth-type-prossibility","fifth-type-prossibility","sixth-type-prossibility"};

    @Test
    public void rtPostObject() throws JSONException, UnsupportedEncodingException {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://172.18.8.43:18101/textClassify";
        String url2="http://localhost:8080/textClassify";
        HttpHeaders headers = new HttpHeaders();
        //headers.add("Content-Type","application/json");
        headers.add("Accept-Language","zh");
        //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String request="东川区应急管理局截至目前未接到安全生产类和自然灾害类的情况报告。";
        System.out.println(request);
        MultiValueMap map = new LinkedMultiValueMap();
        map.add("text",request);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, map, String.class);

        getResponseHeaders(responseEntity);
    }
    public static void getResponseHeaders(ResponseEntity<String> responseEntity) throws JSONException {
        System.out.println("Response Headers:");
        System.out.println(responseEntity.getStatusCode());
        System.out.println("Content-Type:"+responseEntity.getHeaders().get("Content-Type"));
        System.out.println("Transfer-Encoding:"+responseEntity.getHeaders().get("Transfer-Encoding"));
        System.out.println("Date:"+responseEntity.getHeaders().get("Date"));
        System.out.println("Keep-Alive:"+responseEntity.getHeaders().get("Keep-Alive"));
        System.out.println("Connection:"+responseEntity.getHeaders().get("Connection"));
        String result=responseEntity.getBody();
        JSONObject json=new JSONObject(result);
        System.out.println("{");
        for(int i=0;i<6;i++){
            if(json.has(type[i])){
                System.out.println("        \""+type[i]+"\":"+IntToString((String) json.get(type[i])));
                System.out.println("        \""+type_prossibility[i]+"\":"+json.get(type_prossibility[i]));
            }
        }
        System.out.println("}");
    }

    public static String IntToString(String label){
        switch(label){
            case "0001":
                return "安全生产";
            case "0002":
                return "社会安全";
            case "0003":
                return "自然灾害";
            case "0004":
                return "公共卫生";
            case "0005":
                return "通知";
            case "0006":
                return "其他";
        }
        return "null";
    }
    public static String getContext(String path){
        List<String> list = new ArrayList<String>();
        try
        {
            String encoding = "utf-8";
            File file = new File(path);
            if (file.isFile() && file.exists())
            { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;

                while ((lineTxt = bufferedReader.readLine()) != null)
                {
                    list.add(lineTxt);
                }
                bufferedReader.close();
                read.close();
            }
            else
            {
                System.out.println("找不到指定的文件");
            }
        }
        catch (Exception e)
        {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

        String result=list.toString();
        return result;
    }
}

```

- 执行成功之后输出文本分类的结果以及响应报文的内容，其中 > http://172.18.8.43:18101/textClassify 是公司内网服务器的地址。
- 如果想要通过外网使用该服务，这时候的URL地址为：http://119.3.24.91:80/textClassify 。 这是华为云服务器提供的服务，使用期到2022年9月2日。

### 如何更新服务
#### 本地项目
- 如果需要对服务进行更改，那么需要下载开源项目，其中文本分类实现中所需要用到的文件有main/java/com/hancks/hanlp/classification/classifiers下三个分类器文件、main/java/com/hancks/hanlp/classification/utilities/CollectionUtility.java文件和main/java/com/hancks/hanlp/TextClassify.java文件
- 在本地修改并成功执行之后，通过以下命令

```
mvn clean package
```
将项目打包，这时候在target目录下面生成了三个jar文件，这时候通过

```
java -jar hanlp-1.8.3.jar
```
命令执行即可。

#### 内网服务器
- 如果需要更改内网服务器上面的服务，步骤和上面相同，但是打包文件之后，只需要替换服务器中的“hanlp-1.8.3.jar”文件即可，其他文件不需要任何修改。
### 服务接口文档
#### 接口描述
- 接口请求URL：http:172.18.8.43:18101/textClassify。
- 文本分类接口能够对用户输入的文本进行自动分类，将其映射到具体的类目上，用户只需要提供待分类的文本，而无需关注具体的实现。通过对文本内容进行分析、处理、归纳和推理，识别出文本可能的具体业务类型（可能是一种，也可能是多种），并且提供该类型的概率。在这里业务类型一共包含六种，分别为安全生产、社会安全、自然灾害、公共卫生、通知、其他。
#### 请求参数
|参数名称| 必选 | 类型 | 描述 |
|---|---|---|---|
| text | 是 | String | 待分类的文本，仅支持UTF-8格式的中文文本 |
#### 输出参数
输出的结果是一个map类型，包含下面两类key值
| 参数名称 | 类型 | 描述 |
|---|---|---|
| type | String | 这是文本分类的结果类型 |
| type_prossibility | String | 类型的概率，是将一个double类型的变量转换而成 |

- 注意这里的type输出的内容并不是“安全生产”等字样，而是“0001/0002/0003/0004/0005/0006”这几种字样，其中具体映射为“0001：安全生产、0002：社会安全、0003：自然灾害、0004：公共卫生、0005：通知、0006：其他”，需要在客户端进行转换。
- 其中type的可能值
```
    public static final String[] type= {"first-type","second-type","third-type","forth-type","fifth-type","sixth-type"};
```
- type_prossibility的可能值

```
    public static final String[] type_prossibility= {"first-type-prossibility","second-type-prossibility","third-type-prossibility",
            "forth-type-prossibility","fifth-type-prossibility","sixth-type-prossibility"};
```

#### 输入示例
POST请求：
```
http://172.18.8.43:18101/textClssify?text=安全生产
```
GET请求
```
http://172.18.8.43:18101/textClassify/安全生产
```
#### 输出示例
输出结果（未做任何调整）
```
{
        "second-type":"0003",
        "second-type-prossibility":"0.3138506888148521",
        "third-type-prossibility":"0.05242666784309736",
        "first-type-prossibility":"0.6260985632103986",
        "third-type":"0004",
        "first-type":"0001"
}
```
将文本类型做了调整后的输出结果

```
{
        "first-type":安全生产
        "first-type-prossibility":0.6260985632103986
        "second-type":自然灾害
        "second-type-prossibility":0.3138506888148521
        "third-type":公共卫生
        "third-type-prossibility":0.05242666784309736
}
```
