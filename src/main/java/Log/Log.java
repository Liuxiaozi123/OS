package Log;

import PCB.PCB;
import lombok.Data;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
@Data
public class Log {
    //调度日志
    private File file;
    public Log(File file){
        this.file=file;
    }
    public void WriteLog(PCB pcb, String model, Date date) throws IOException {
        String log="";
        String time;//时间记录
        String dt="yyyy MM:dd:HH mm:ss:SSS";
        SimpleDateFormat dateFormat=new SimpleDateFormat(dt);
        time=dateFormat.format(date);
        switch(model)
        {
            case "R":
                log="调度进程到就绪队列";
                break;
            case "D":
                log="进程结束";
                break;
            case "I":
                log="调度进程到输入等待队列";
                break;
            case "O":
                log="调度进程到输出等待队列";
                break;
            case "W":
                log="调度进程到等待队列";
                break;
        }
        BufferedWriter writer=new BufferedWriter(new FileWriter(file,true));
        writer.write(time+""+log+"进程名"+pcb.getM_name());
        writer.newLine();
        writer.close();
    }


}
