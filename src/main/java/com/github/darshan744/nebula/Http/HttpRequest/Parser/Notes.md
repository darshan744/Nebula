## Notes on Parsing Using BufferedReader and InputStream Reader

# BufferedReader
>The Buffered Reader reads the InputStreamReader as Characters not as Bytes.
> Http is a Byte related Protocol. The Content-Length mentioned in the Headers is the actually bytes length of the Body
> not as Character length for Example consider below body
```
    "{\r\n" +
     "\"name\":\"John Doe\",\r\n" +
     "\"email\":\"john@example.com\r\n" +
     "\"}"
```
> In the above example the content length mentioned will be counted along with \r\n
> and quotes so the content length will be around 48 
> But buffer reader wil read the complete line and ignores \r counting only with \n
> So its best to use InputStream since it works on bytes 