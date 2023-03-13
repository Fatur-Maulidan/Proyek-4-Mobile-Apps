package HandlerCustom

import java.io.File
import java.io.FileOutputStream

class FileHandler {
    fun checkFileIsExits(file: File): FileOutputStream {
        if(!file.exists()){
            file.createNewFile()
        }
        return FileOutputStream(file)
    }
}