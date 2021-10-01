# Android-Studio

## Social Touch Project
This app have 3 screens : Friend, Public, Profile
Welcome to download the **SocialTouch_Presentation.pptx** for more details.

[Social-Touch Prototype](https://www.figma.com/file/5cDnkr50NecNNPb8w8xgmW/Android-app?node-id=0%3A1)

## Classes
### Main
`MainActivity : AppCompatActivity()` -  setting the bottom navigation,“onOptionsItemSelected()”(back 
button) and “onCreateOptionsMenu()” (create the menu)<br>

### Friend
`Friend(_image:Int?,_name:String,_id:String)` -  is a class that use for store object in arraylist<br>

`FriendFragment : Fragment()` -  – it will init the recycle view, and get ready the require get data from database (create the menu)<br>

`AddFriendDialogFragment : DialogFragment()` -  this is to show up, when use click “+”, then the dialog will let you to input the id to add new friend<br>

`DeleteFriendDialogFragment(_ targetIDPosition:Int) : DialogFragment()` -  this is to show up, when use click “delete”, then dialog will make sure do you really want to delete friend. <br>

`FriendRecycleAdapter : RecyclerView.Adapter<FriendRecycleAdapter.ViewHolder>()` -  this is to init the card view, assign the data (create the menu)<br>

### Public
`PublicSocial(_img:Int?,_name:String,_message:String)` -  is a class that use for store object in arraylist <br>

`PublicFragment : Fragment()` -  it will init the recycle view, and get ready the require get data from database <br>

`publicSocialDialogFragment(_publicSocialList : ArrayList<PublicSocial>) : DialogFragment()` - it shows up the when user want to publish the message in public social, then put the data into the dialog. <br>

`publicSocialRecycleAdapter :  RecyclerView.Adapter<FriendRecycleAdapter.ViewHolder>()` - this is to init the card view, assign the data.<br>


### Profile
`ProfileFragment : Fragment()` -   this is to init the profile require data, also save the change data to database (Firebase).<br>



### Communicate
`message_contact : Fragmemt()` -  user communicate with friend, send message to each other, and update the data to the database, then app will update the data when database update.<br>

`messageRecycleAdapter : RecyclerView.Adapter<FriendRecycleAdapter.ViewHolder>()` - this is to show the user message box, if friend’s message, message box will show in left, if user message box, then it will show in right<br>
