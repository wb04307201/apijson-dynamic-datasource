/*Copyright ©2016 TommyLemon(https://github.com/TommyLemon/APIJSON)
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package cn.wubo.apijson.dynamic.datasource.controller;

import apijson.RequestMethod;
import apijson.StringUtil;
import apijson.framework.APIJSONController;
import apijson.orm.Parser;
import cn.wubo.apijson.dynamic.datasource.controller.dto.RecordDTO;
import cn.wubo.apijson.dynamic.datasource.framework.ADDTransactional;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;


/**
 * 请求路由入口控制器，包括通用增删改查接口等，转交给 APIJSON 的 Parser 来处理
 * 具体见 SpringBoot 文档
 * https://www.springcloud.cc/spring-boot.html#boot-features-spring-mvc
 * 以及 APIJSON 通用文档 3.设计规范 3.1 操作方法
 * https://github.com/Tencent/APIJSON/blob/master/Document.md#3.1
 * <br > 建议全通过HTTP POST来请求:
 * <br > 1.减少代码 - 客户端无需写HTTP GET,PUT等各种方式的请求代码
 * <br > 2.提高性能 - 无需URL encode和decode
 * <br > 3.调试方便 - 建议使用 APIAuto(http://apijson.cn/api) 或 Postman
 *
 * @author Lemon
 */
@RestController
@RequestMapping("")
public class DemoController extends APIJSONController<Long> {

    @Override
    public Parser<Long> newParser(HttpSession session, RequestMethod method) {
        return super.newParser(session, method).setNeedVerify(false);  // TODO 这里关闭校验，方便新手快速测试，实际线上项目建议开启
    }

    /**
     * 批量处理增删改查操作
     *
     * 该方法用于批量处理数据库的增删改查操作，通过接收一个包含多个记录的列表，
     * 并利用会话进行数据库操作，返回一个包含所有操作结果的JSONArray对象
     *
     * @param records 一个包含多个RecordDTO的列表，每个RecordDTO代表一个数据库操作
     * @param session HTTP会话对象，用于在多个请求之间保持状态
     * @return 返回一个JSONArray对象，包含每个操作的结果
     *
     * 注意：该方法使用了自定义的ADDTransactional注解，可能需要额外的配置来支持这种事务控制方式
     *
     * 可能的改进方向：
     * - 考虑增加操作类型（增删改查）的枚举或者常量，以提高代码的可读性和可维护性
     * - 对于错误处理，目前是直接抛出运行时异常，可以考虑返回具体的错误代码或者使用HTTP状态码来表示错误
     */
    @PostMapping(value = "saveBatch")
    @ADDTransactional
    public JSONArray saveBatch(@RequestBody List<RecordDTO> records, HttpSession session) {
        JSONArray ja = new JSONArray();
        for (RecordDTO rec : records) {
            JSONObject jo = JSON.parseObject(super.crud(rec.getMethod(), rec.getData().toJSONString(), session));
            if (jo.containsKey("code") && jo.getInteger("code") != 200) throw new RuntimeException(jo.getString("msg"));
            ja.add(jo);
        }
        return ja;
    }

    /**
     * 增删改查统一接口，这个一个接口可替代 7 个万能通用接口，牺牲一些路由解析性能来提升一点开发效率
     *
     * @param method
     * @param request
     * @param session
     * @return
     */
    @PostMapping(value = "{method}")
    // 如果和其它的接口 URL 冲突，可以加前缀，例如改为 crud/{method} 或 Controller 注解 @RequestMapping("crud")
    @Override
    public String crud(@PathVariable String method, @RequestBody String request, HttpSession session) {
        return super.crud(method, request, session);
    }

    /**
     * 增删改查统一接口，这个一个接口可替代 7 个万能通用接口，牺牲一些路由解析性能来提升一点开发效率
     *
     * @param method
     * @param tag
     * @param params
     * @param request
     * @param session
     * @return
     */
    @PostMapping("{method}/{tag}")
    // 如果和其它的接口 URL 冲突，可以加前缀，例如改为 crud/{method}/{tag} 或 Controller 注解 @RequestMapping("crud")
    @Override
    public String crudByTag(@PathVariable String method, @PathVariable String tag, @RequestParam Map<String, String> params, @RequestBody String request, HttpSession session) {
        return super.crudByTag(method, tag, params, request, session);
    }

    /**
     * 获取
     * 只为兼容HTTP GET请求，推荐用HTTP POST，可删除
     *
     * @param request 只用String，避免encode后未decode
     * @param session
     * @return
     * @see {@link RequestMethod#GET}
     */
    @GetMapping("get/{request}")
    public String openGet(@PathVariable String request, HttpSession session) {
        try {
            request = URLDecoder.decode(request, StringUtil.UTF_8);
        } catch (Exception e) {
            // Parser 会报错
        }
        return get(request, session);
    }

}