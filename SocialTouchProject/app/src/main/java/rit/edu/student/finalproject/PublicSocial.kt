package rit.edu.student.finalproject


//class that store the object into arrayList
class PublicSocial(_image:Int?,_name:String,_message:String) {
    var name = _name
    var message = _message
    var image = _image
    init {
        image = R.drawable.ic_public    //default icon
    }//init
}//publicsocial