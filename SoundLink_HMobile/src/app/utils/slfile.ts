class SLFile {

  path : string;
  name : string;
  content : Blob;

  constructor(name : string, path : string, content : Blob){ 
    this.name = name;
    this.path = path;
    this.content = content;
  }

}