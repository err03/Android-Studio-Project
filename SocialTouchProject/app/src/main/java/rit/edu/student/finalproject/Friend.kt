package rit.edu.student.finalproject

//class that store the object into arrayList
class Friend(_image:Int?,_name:String,_id:String) {
    var image:Int? = _image
    var name:String = _name
    var id:String = _id

    init {
        image = R.drawable.ic_friend    //default icon
    }
}