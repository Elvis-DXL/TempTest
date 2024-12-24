package test.word;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;

public class WordTest {

    public static void main(String[] args) throws Exception {
        mainTwo();
    }

    public static void mainTwo() throws Exception {
        String html = "<html>\n" +
                "<body>\n" +
                "<span style=\"font-family:黑体;font-size:50px\"><p>测试<sub>2</sub></p></span></br>\n" +
                "<span><img src='https://ssszkj.com:29000/transformation-of-scientific/upload/20240716/3bef986f1152a2cd33fea2fd89038a40.png'/>\n" +
                "<span style=\"font-family:黑体;font-size:50px\"><p><br></p><p>4月25日，2024中关村论坛年会在北京开幕，十项重大科技成果随之发布。中关村世界领先科技园区建设方案发布、国际首个全模拟光电智能计算芯片亮相、人工智能取得系列成果、量子云算力集群升级、300兆瓦级F级重型燃气轮机首台样机总装下线……十项重大科技成果不仅展示了当前这一领域的领先水平，也为新质生产力注入了科技创新的强大动能。</p><p><img src=\"https://pics3.baidu.com/feed/95eef01f3a292df5fed08440ce4cb66d35a873da.jpeg@f_auto?token=5fa931b2fd25e331f2d2b839c0daa64e\" alt=\"\" data-href=\"\" style=\"width: 699.734px;\"/><img src=\"https://pics6.baidu.com/feed/e7cd7b899e510fb381a24365b44e2298d0430c17.jpeg@f_auto?token=46fcc1f12100525bc6ded96cebb8365c\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ssszkj.com:29000/transformation-of-scientific/upload/20240722/d375cc3ff81979d24ed0cd7f0e66cc66.png\" alt=\"\" data-href=\"\" style=\"\"/></p><p><br></p><p style=\"text-align: center;\">重大科技成果一</p><p style=\"text-align: center;\"><br></p><p>《中关村世界领先科技园区建设方案（2024—2027年）》发布</p><p><br></p><p>划定50项重点任务</p><p><br></p><p>习近平总书记高度重视中关村创新发展，强调要加快建设世界领先科技园区。为深入贯彻落实习近平总书记重要指示精神，4月25日，工业和信息化部、科学技术部和北京市人民政府对外发布《中关村世界领先科技园区建设方案（2024—2027年）》，方案提出，到2027年中关村初步建成世界领先科技园区，2035年全面建成世界领先科技园区。</p><p><br></p><p>方案围绕以理念领先带动原始创新、人才发展、一流企业、高端产业、开放创新生态全面领先，部署五方面50项重点任务，推出新一批重大政策和改革措施，将中关村打造成为北京国际科技创新中心建设跃升的主阵地、京津冀协同发展的突破口、中国高质量发展的引领者、全球创新网络的关键枢纽，为中国式现代化建设贡献力量。</p><p><br></p><p>整体来看，到2027年，中关村将力争在生命科学等领域达到全球领先水平，在颠覆性技术和关键核心技术上实现重大突破；打造高水平人才集聚地，人才梯队体系较为完备，成为全球向往的创业乐土；成为初创企业孕育地、全球领先的创新型企业栖息地，在重点领域持续涌现世界一流企业；基本形成新一代信息技术、医药健康、智能装备、绿色智慧能源4个具有技术主导权的世界级产业集群，推进制造业重点产业链高质量发展，超前布局引领世界发展的未来产业；成为国际创业投资机构集聚区，创业投资基金规模世界领先；具有全球竞争力的开放创新生态全面形成，打造产业高度集聚、开放创新活跃、机制高效有力、基础设施完善、环境清新优美的中国特色社会主义创新园区，成为世界科技园区发展的重要标杆。</p><p><img src=\"https://pics6.baidu.com/feed/e7cd7b899e510fb381a24365b44e2298d0430c17.jpeg@f_auto?token=46fcc1f12100525bc6ded96cebb8365c\" alt=\"\" data-href=\"\" style=\"width: 699.734px;\"/></p>\n" +
                "</span></body>\n" +
                "</html>";
        html = "<html><body></body></html>";
        try {
            // 生成doc格式的word文档，需要手动改为docx
            byte by[] = html.getBytes("UTF-8");
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(by);
            POIFSFileSystem poifsFileSystem = new POIFSFileSystem();
            DirectoryEntry directoryEntry = poifsFileSystem.getRoot();
            directoryEntry.createDocument("WordDocument", byteArrayInputStream);
            // 文件路径
            String fileUrl = "D:/TDDOWN/PjoTest/WordTest.doc";
            // 保存doc文档
            FileOutputStream outputStream = new FileOutputStream(fileUrl);
            poifsFileSystem.writeFilesystem(outputStream);
            byteArrayInputStream.close();
            outputStream.close();
//            file = new File(fileUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void mainOne() throws Exception {
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = paragraph.createRun();
        run.setText("WordTest标题");
        run.setFontFamily("黑体");
        run.setFontSize(29);
        run.setBold(true);

        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.setText("WordTest正文");
        run.setText("宋体");
        run.setFontSize(15);

        ex(document);
    }

    public static void ex(XWPFDocument document) throws Exception {
        FileOutputStream fos = new FileOutputStream("D:/TDDOWN/PjoTest/WordTest.docx");
        document.write(fos);
    }
}