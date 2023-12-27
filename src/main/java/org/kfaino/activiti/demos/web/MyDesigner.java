package org.kfaino.activiti.demos.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ActivitiException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.UUID;

@RestController
public class MyDesigner {

    protected static final Logger LOGGER = LoggerFactory.getLogger(MyDesigner.class);
   
    @RequestMapping("/create")
    public void createModel(HttpServletRequest request, HttpServletResponse response) {
        try {
            UUID uuid = UUID.randomUUID();
            response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value="/model/{modelId}/json", method = RequestMethod.GET, produces = "application/json")
    public ObjectNode initJson(){

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode modelObjectNode = mapper.createObjectNode();

        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, "hello1111");
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, "hello1111");
        modelObjectNode.put(ModelDataJsonConstants.MODEL_ID, "5001");

        ObjectNode objectNode = mapper.createObjectNode();

        objectNode.put("resourceId","canvas11111111111");
        objectNode.put("id","canvas11111111111");

        ObjectNode stencilset = mapper.createObjectNode();
        stencilset.put("namespace","http://b3mn.org/stencilset/bpmn2.0#");
        objectNode.set("stencilset",stencilset);

        modelObjectNode.set("model",objectNode);

        System.out.println(modelObjectNode.toString());

        return modelObjectNode;
    }

    @RequestMapping(value="/model/{modelId}/save", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void saveModel(@PathVariable String modelId, String name, String description, String json_xml, String svg_xml){
//        @RequestBody MultiValueMap<String, String> values
        LOGGER.info("modelId | {}", modelId);
        LOGGER.info("name | {}", name);
        LOGGER.info("description | {}", description);
        LOGGER.info("json_xml | {}", json_xml);
        LOGGER.info("svg_xml | {}", svg_xml);

    }


//    @RequestMapping(value="/editor/stencilset", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public @ResponseBody String getStencilset() {
        InputStream stencilsetStream = this.getClass().getClassLoader().getResourceAsStream("static/stencilset.json");
        try {
            return IOUtils.toString(stencilsetStream, "utf-8");
        } catch (Exception e) {
            throw new ActivitiException("Error while loading stencil set", e);
        }
    }
}