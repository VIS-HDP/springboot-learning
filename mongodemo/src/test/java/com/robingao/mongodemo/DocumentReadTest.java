package com.robingao.mongodemo;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DocumentReadTest {

    @Autowired
    DocRepository docRepository;

    @Test
    public void test(){

        StringBuffer buffer = new StringBuffer("");
        String title = "";
        try {
            File file = new File("E:\\policy-3.docx");
            XWPFDocument xwpfDocument = new XWPFDocument(new FileInputStream(file));
            XWPFParagraph para;

            Iterator<XWPFParagraph> iterator = xwpfDocument.getParagraphsIterator();
            int i = 0 ;
            while (iterator.hasNext()) {
                para = iterator.next();
                if(i == 0 ){
                    title = para.getText();
                }
                //System.out.println("===="+para.getText());
                buffer.append(para.getText()+"\n");
                i++;
            }
            System.out.println("==end ============="+buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        NewPolicyPdfs newPolicyPdfs = new NewPolicyPdfs();
        newPolicyPdfs.setPolicyInfo(buffer.toString());
        newPolicyPdfs.setPolicyDate("2018-08-01");
        newPolicyPdfs.setPolicyTitle(title);
        newPolicyPdfs.setPolicyType("退役军人政策");
        ArrayList<String> t = new ArrayList<>();
        t.add("退役");
        t.add("军人");
        //t.add("就业");
        t.add("光荣牌");
        newPolicyPdfs.setPolicyTags(t);
        NewPolicyPdfs ret = docRepository.save(newPolicyPdfs);

        System.out.println(ret.toString());

        System.out.println("===========end ================");
    }

}
