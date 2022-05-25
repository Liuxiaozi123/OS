package Log;

import PCB.PCB;
import lombok.Data;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
@Data
public class Log {
    //������־
    private File file;
    public Log(File file){
        this.file=file;
    }
    public void WriteLog(PCB pcb, String model, Date date) throws IOException {
        String log="";
        String time;//ʱ���¼
        String dt="yyyy MM:dd:HH mm:ss:SSS";
        SimpleDateFormat dateFormat=new SimpleDateFormat(dt);
        time=dateFormat.format(date);
        switch(model)
        {
            case "R":
                log="���Ƚ��̵���������";
                break;
            case "D":
                log="���̽���";
                break;
            case "I":
                log="���Ƚ��̵�����ȴ�����";
                break;
            case "O":
                log="���Ƚ��̵�����ȴ�����";
                break;
            case "W":
                log="���Ƚ��̵��ȴ�����";
                break;
        }
        BufferedWriter writer=new BufferedWriter(new FileWriter(file,true));
        writer.write(time+""+log+"������"+pcb.getM_name());
        writer.newLine();
        writer.close();
    }


}
