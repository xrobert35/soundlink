function Song(){
    
    var id;
    var title;
    var artist;
    var duration;
    var url;
    var trackNumber;
   
    this.getId = function(){
        return this.id;
    }
    
    this.setId = function(id){
        this.id = id;
    }
    
    this.getArtist = function() {
        return this.artiste;
    }
    
    this.setArtist = function(artist){
        this.artist = artist;
    } 
    
    this.getTitle = function() {
        return this.title;
    }
    
    this.setTitle = function(title){
        this.title = title;
    } 
    
    this.getUrl = function() {
        return this.url;
    }
    
    this.setUrl = function(url){
        this.url = url;
    }
    
    this.getDuration = function() {
        return this.duration;
    }
    
    this.setDuration = function(duration){
        this.duration = duration;
    }
    
    this.setTrackNumber = function(trackNumber){
        this.trackNumber = trackNumber;
    }
    
    this.getTrackNumber = function(){
        return this.trackNumber;
    }
}

Song.prototype.toString = function songToString() {
	var cache = [];
	return JSON.stringify(this, function(key, value) {
		if (typeof value === 'object' && value !== null) {
			if (cache.indexOf(value) !== -1) {
				return;
			}
			cache.push(value);
		}
		return value;
	});
};
