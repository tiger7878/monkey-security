package com.monkey.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.core.io.ClassPathResource;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

/**
 * wire的客户端，可以伪造很多操作
 *
 * @author: monkey
 * @date: 2018/10/7 15:22
 */
public class MockServer {

    //运行这里就可以向wiremock服务器发指令，然后在url上访问就可以得到结果
    //多个main，在打包时会报错，先注释掉
    /*public static void main(String[] args) throws IOException {
        WireMock.configureFor(8062);//指定服务器的端口
        WireMock.removeAllMappings();//清空以前的所有配置

        mock("/order/1", "01");
        mock("/order/2", "02");
    }*/

    private static void mock(String url, String file) throws IOException {
        ClassPathResource resource = new ClassPathResource("mock/response/" + file + ".txt");
        String content= StringUtils.join(FileUtils.readLines(resource.getFile(),"UTF-8").toArray(),"\n");
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(url)).willReturn(WireMock.aResponse().withBody(content).withStatus(200)));
    }


}
