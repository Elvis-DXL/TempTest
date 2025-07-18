package test.ai;

public class AITest {
    public static void main(String[] args) throws Exception {
        System.out.println(AIBusiness.newInstance("http://192.168.2.38:11434", "qwen2.5:14b", null, true, null)
                .execute("你是一个考试评审专家，请根据以下内容评估考生答案,总分100分，仅输出json，输出结构如下：\n" +
                         "                {\n" +
                         "                  \"totalScore\": int,\n" +
                         "                  \"completeness\": \"高/中/低\",\n" +
                         "                  \"correctness\": \"高/中/低\",\n" +
                         "                  \"accuracy\": \"高/中/低\",\n" +
                         "                  \"deductions\": [\"...\"]\n" +
                         "                }\n" +
                         "                参考答案：中国共产党好\n" +
                         "                考生答案：中国共产党真好"));
    }
}