//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package ${basepackage}.common.restful;

import ${basepackage}.domain.BizData4Page;
import ${basepackage}.domain.ResponseT;
import ${basepackage}.domain.MsgInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class ZyjExtFastJsonHttpMessageConverter<T> extends AbstractHttpMessageConverter<T> {
    public static final Charset UTF8 = Charset.forName("UTF-8");
    private Charset charset;


    public ZyjExtFastJsonHttpMessageConverter() {
        super(new MediaType[]{
                new MediaType("application", "json", UTF8),
                new MediaType("application", "*+json", UTF8)}
        );
        this.charset = UTF8;
    }

    protected boolean supports(Class<?> clazz) {
        return true;
    }

    protected T readInternal(Class<? extends T> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream in = inputMessage.getBody();
        //读4K
        byte[] buf = new byte[1024 * 1024 * 4];
        int len = 0;
        while ((len = in.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        byte[] bytes = baos.toByteArray();
        return JSON.parseObject(bytes, 0, bytes.length,
                this.charset.newDecoder(), clazz, new Feature[0]);

    }

    protected void writeInternal(T t, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

        ResponseT response = new ResponseT();

        if (t instanceof MsgInfo) {
            MsgInfo msg = (MsgInfo) t;
            response.setMsg(msg.getS());
            response.setRtnCode(msg.getRtnCode());
        } else if (t instanceof BizData4Page) {
            BizData4Page o = (BizData4Page) t;
            // 不输出查询条件
            o.setConditions(null);
            response.setBizData(o);
        } else {
            response.setBizData(t);
        }
        OutputStream out = outputMessage.getBody();
        String text = JSON.toJSONString(response,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.DisableCircularReferenceDetect);
//		byte[] bytes = text.getBytes(this.charset);
        out.write(text.getBytes());
    }
}
