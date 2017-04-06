import { Injectable } from '@angular/core';
import { File } from '@ionic-native/file';
import { FileChooser } from '@ionic-native/file-chooser';
import { SLFile} from 'file';

@Injectable()
export class FileManager {

  constructor(private file: File, private fileChooser : FileChooser) {}

  writeSLFile(fileToWrite : SLFile){
    this.createNecessaryDir(fileToWrite).then(result =>{
       return this.writeFile(fileToWrite);
    });
  }
  
  deleteSLFile(fileToDelete : SLFile){
    return this.file.removeFile(this.file.externalApplicationStorageDirectory + fileToDelete.path, fileToDelete.name);
  }

  chooseFile(){
    return this.fileChooser.open();
  }

  private writeFile(fileToWrite : SLFile){
    return this.file.writeFile(this.file.externalApplicationStorageDirectory + fileToWrite.path, fileToWrite.name, fileToWrite.content, false);
  }

  private createNecessaryDir(fileToWrite){
    return this.file.createDir(this.file.externalApplicationStorageDirectory, fileToWrite.path, false);
  }
}