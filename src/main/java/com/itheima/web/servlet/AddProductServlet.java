package com.itheima.web.servlet;

import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.UUIDUtils;
import com.itheima.web.view.Result;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/addProduct")
public class AddProductServlet extends BaseServlet {
    private ProductService productService= BeanFactory.newInstance(ProductService.class);

    public void addProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> map = getMap(request);

        Product product = new Product();

        try {
            BeanUtils.populate(product,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String date = sdf.format(new Date());

        product.setPdate(date);

        product.setPflag(0);

        product.setPid(UUIDUtils.getUUID());

        productService.addProduct(product);

        Result result = new Result(Result.SUCCESS, "商品信息添加成功");

        String s = JSONObject.fromObject(result).toString();

        response.getWriter().write(s);
    }

    public Map<String, String[]> getMap(HttpServletRequest request) {
        Map<String,String[]> map=new HashMap<>();

        try {
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();

            ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);

            List<FileItem> fileItems = fileUpload.parseRequest(request);

            for (FileItem fileItem : fileItems) {

                if(fileItem.isFormField()){

                    String name = fileItem.getFieldName();

                    String value = fileItem.getString("utf-8");

                    map.put(name,new String[]{value});
                }else {
                    String name = fileItem.getFieldName();

                    String filename=fileItem.getName();

                    InputStream ips = fileItem.getInputStream();

                    ResourceBundle bundle = ResourceBundle.getBundle("rootDir");

                    String rootDir=bundle.getString("rootdir");

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    String date = sdf.format(new Date());

                    File rootdir = new File(rootDir+"\\"+date);

                    if(!rootdir.exists()){

                        rootdir.mkdir();
                    }

                    String houzui = filename.split("\\.")[1];

                    filename = UUID.randomUUID().toString().replace("-", "") + "." + houzui;

                    File file = new File(rootdir, filename);

                    FileOutputStream fos = new FileOutputStream(file);

                    IOUtils.copy(ips,fos);

                    IOUtils.closeQuietly(ips);

                    IOUtils.closeQuietly(fos);

                    String path = file.getAbsolutePath();

                    int index = path.indexOf("resources");

                    String pimage = path.substring(index);

                    map.put(name,new String[]{pimage});
                }
                fileItem.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

}
