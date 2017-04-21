import { Injectable } from '@angular/core';
import { File } from '@ionic-native/file';
import { FileChooser } from '@ionic-native/file-chooser';
import { SLFile } from 'file';
import { FilePath } from '@ionic-native/file-path';

@Injectable()
export class FileManager {

  constructor(private file: File, private fileChooser: FileChooser, private filePath: FilePath) { }

  writeSLFile(fileToWrite: SLFile) {
    this.createNecessaryDir(fileToWrite).then(result => {
      return this.writeFile(fileToWrite);
    });
  }

  deleteSLFile(fileToDelete: SLFile) {
    return this.file.removeFile(this.file.externalApplicationStorageDirectory + fileToDelete.path, fileToDelete.name);
  }

  chooseFile() {
    return this.fileChooser.open().then((fileUri) => {
      return this.filePath.resolveNativePath(fileUri);
    });
  }

  toBase64(filePath): Promise<string> {
    return new Promise((resolve, reject) => {
      (<any>window).resolveLocalFileSystemURL(filePath, (fileEntry) => {
        fileEntry.file((file) => {
          var reader = new FileReader();
          reader.onloadend = (evt: any) => {
            var content = reader.result;
            resolve(content);
          }
          reader.readAsDataURL(file);
        });
      });
    });
  }

  private writeFile(fileToWrite: SLFile) {
    return this.file.writeFile(this.file.externalApplicationStorageDirectory + fileToWrite.path, fileToWrite.name, fileToWrite.content, false);
  }

  private createNecessaryDir(fileToWrite) {
    return this.file.createDir(this.file.externalApplicationStorageDirectory, fileToWrite.path, false);
  }
}