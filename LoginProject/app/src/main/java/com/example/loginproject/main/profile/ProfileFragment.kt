package com.example.loginproject.main.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.loginproject.MainActivity.Companion.db
import com.example.loginproject.R
import com.example.loginproject.data.interfaces.Contract
import com.example.loginproject.data.network.AvatarInfo
import com.example.loginproject.data.network.UserInfo
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_profile.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class ProfileFragment : Fragment(), Contract.ProfileView {
    private val GALLERY_REQUEST_CODE = 1234
    private val AVATAR_CAMERA_PERMISSION_REQUEST = 201
    private val AVATAR_WRITE_PERMISSION_REQUEST = 202

    private var selectedPhotoFile: File? = null
    lateinit var imageUri: Uri

    private val presenter: Contract.ProfilePresenter by inject{ parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(!db.haveUserInfo){
//            first start or user logged out
            profilepage.visibility = View.INVISIBLE
            Toast.makeText(context, "haveUserInfo false", Toast.LENGTH_SHORT).show()
        }else{
            progressBar2.visibility = View.INVISIBLE
            tvLoad.visibility = View.INVISIBLE
            btnSave.visibility = View.INVISIBLE
            btnStart.visibility = View.INVISIBLE
            changeDesign()
//            TODO start invisible
        }
        btnStart.setOnClickListener{
            findNavController().navigate(ProfileFragmentDirections.toLogin())
        }
        btnLogout.setOnClickListener{
            logOut()
        }
        ivAvatar.setOnClickListener{
            imageChooserDialog()
        }
        btnChangePwd.setOnClickListener{
//            if(db.sid.isEmpty()){
//                showText.text = "Server did not give access for sid"
//            }else{
//                db.verifyType = "reset"
//                findNavController().navigate(ProfileFragmentDirections.toReset())
//            }
            db.verifyType = "reset"
            findNavController().navigate(ProfileFragmentDirections.toReset())

        }
        btnSave.setOnClickListener{
            getPermissionsToWrite()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    override fun onStart() {
        super.onStart()
        Log.i("MSG", "Profile on start")
    }
//------------------------------------------PERMISSIONS---------------------------------------------
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == AVATAR_CAMERA_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickFromCamera()
            }
            return
        }
//          TODO READ WRITE PERMISSION
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {

            GALLERY_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.data?.let { uri ->
                        launchImageCrop(uri)
                    }
                }
                else{
                    Log.i("MSG", "Image selection error" )
                }
            }
            AVATAR_CAMERA_PERMISSION_REQUEST -> {
                if (resultCode == Activity.RESULT_OK) {
//                    data?.extras?.get("data").
                    selectedPhotoFile?.let { file ->
                        Log.i("MSG", "camera req " + file.path)

//                        val myBitmap = BitmapFactory.decodeFile(file!!.getAbsolutePath())
//                        ivAvatar2.setImageBitmap(myBitmap)
                        launchImageCrop(Uri.fromFile(file))
//                        saveImageToStorage(myBitmap)
                    }
                }
                else{
                    Log.i("MSG", "Image selection error: Couldn't select that image from memory. Camera" )
                }
            }
            AVATAR_WRITE_PERMISSION_REQUEST ->{
                saveAva()
            }
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)
                if (resultCode == Activity.RESULT_OK) {
                    setImage(result.uri)
                    imageUri = result.uri
                    btnSave.visibility = View.VISIBLE
                }
                else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Log.i("MSG", "Crop error: ${result.getError()}" )
                }
            }
        }
    }


    private fun getPermissionsForCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val cameraGranted = ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
            if (cameraGranted) {
                pickFromCamera()
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.CAMERA),
                    AVATAR_CAMERA_PERMISSION_REQUEST
                )
            }
        } else {
            pickFromCamera()
        }
    }
    private fun getPermissionsToWrite() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val writeGranted = ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
            if (writeGranted) {
                saveAva()
            } else {
                requestPermissions(
                    arrayOf( Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    AVATAR_WRITE_PERMISSION_REQUEST
                )
            }
        } else {
            saveAva()
        }
    }


//------------------------------------------INTERFACE VIEW------------------------------------------

    override fun userResponse(userInfo: UserInfo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun avaResponse(avatarInfo: AvatarInfo) {
        Log.i("MSG", "avarespo " + avatarInfo)
//        setting new ava
        db.userInfo.avatar = avatarInfo
        Toast.makeText(getContext(),"New ava saved ",Toast.LENGTH_SHORT).show()
        activity?.window?.clearFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        progressBar2.visibility = View.GONE
        tvLoad.visibility = View.INVISIBLE
    }

    override fun dataFlowWait() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleError(type: String) {
        showText.text = type

    }
//------------------------------------------CHOOSE AVA OPTIONS--------------------------------------

    private fun pickFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }
    private fun pickFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val imageFile = File.createTempFile("avatar", ".jpg", context?.cacheDir)
        val avatarUri = this.context?.let {
            FileProvider.getUriForFile(
                it,
                "${context?.packageName}.provider",
                imageFile
            )
        }
        selectedPhotoFile = imageFile
        intent.putExtra(MediaStore.EXTRA_OUTPUT, avatarUri)
        startActivityForResult(intent, AVATAR_CAMERA_PERMISSION_REQUEST)
    }
//    pick from saved old avatars
    private fun oldAvatarChooser() {
        val adapter: ArrayAdapter<String> =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1)
        adapter.addAll(showSavedAvatars())

        AlertDialog.Builder(requireContext())
            .setTitle("Change avatar")
            .setAdapter(adapter) { dialog, which ->
                saveOrGetAvaName(showSavedAvatars().get(which), 1)
                imageUri = getAvaFromStorage(saveOrGetAvaName("current", 2))
                ivAvatar.setImageURI(imageUri)
                btnSave.visibility = View.VISIBLE
            }
            .create()
            .show()
    }
    private fun launchImageCrop(uri: Uri){
        CropImage.activity(uri)
            .setGuidelines(CropImageView.Guidelines.ON)
            .setAspectRatio(1920, 1080)
            .setCropShape(CropImageView.CropShape.RECTANGLE)
            .start(requireContext(),this)
//        TODO save cropped image
    }
    private fun setImage(uri: Uri){
        imageUri = uri
        Glide.with(this)
            .load(uri)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(ivAvatar)
    }

    //-----------------------------------SAVE or GET AVA--------------------------------------------

    private fun saveAva(){
        var temp = ivAvatar.drawable
        var myBitmap = (temp as BitmapDrawable).bitmap
        saveAvaLocally(myBitmap)
        saveAvaToServer()
////        TODO

    }
    fun saveAvaToServer(){
        val file = File(imageUri.path)
        Log.i("MSG", "imageUri " + imageUri.path + " " + file.toString())
        val filePart = MultipartBody.Part.createFormData(
            "fileupload",
            file.name,
            RequestBody.create("image/*".toMediaTypeOrNull(), file)
        )
        progressBar2.visibility = View.VISIBLE
        tvLoad.visibility = View.VISIBLE
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        presenter.userAva(filePart,db.token.tokenType + " " + db.token.accessToken)
    }
    fun saveAvaLocally(btmp: Bitmap) {
        val externalStorage = Environment.getExternalStorageState()
        if (externalStorage.equals(Environment.MEDIA_MOUNTED)) {
            val storageDirectory = requireContext().getExternalFilesDir(null).toString()
            Log.i("MSG"," saved ava storage " + storageDirectory)
            val newName = generateAvaName()
            val file = File(storageDirectory, newName)
            try {
                val stream: OutputStream = FileOutputStream(file)
                val myBitmap = btmp
                myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                stream.flush()
                stream.close()
                btnSave.visibility = View.INVISIBLE
                saveOrGetAvaName(newName, 1)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            Toast.makeText(getContext(), "no access to storage", Toast.LENGTH_SHORT).show()

        }
    }
    fun generateAvaName(): String {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_" + ".jpg"
        return imageFileName
    }
    private fun getAvaFromStorage(nameOfAva: String): Uri {
        val storageDirectory = requireContext().getExternalFilesDir(null).toString()
        val file = File(storageDirectory, nameOfAva)
        val currentAva = Uri.parse(file.absolutePath)
        return currentAva
    }
    private fun saveOrGetAvaName(newAvaName: String, op: Number): String {
        val memoryName = "currentAva.txt"
        var file = File(requireContext().getExternalFilesDir(null).toString(), memoryName)
        val isNewFileCreated: Boolean = file.createNewFile()
        if (op.equals(1)) {
            file.writeText("")
            file.writeText(newAvaName)
            return newAvaName
        } else {
            val bufferedReader: BufferedReader = file.bufferedReader()
            val inputString = bufferedReader.use { it.readText() }
            return inputString
        }
    }
//------------------------------------------CHANGE DESIGN-------------------------------------------

    fun changeDesign(){
// TODO change design by db userInfo
        NameValue.text = "Username"
        if(db.typeOfRegister == "PHONE"){
            UsernameValue.text = db.userInfo.phone_number
        }else
            UsernameValue.text = db.userInfo.email
//        if(db.userInfo.avatar?.normal?.isNotBlank()!!){
//            setImage(Uri.parse(db.userInfo.avatar?.original))
//        }
        btnColorDesign()
        if(db.userInfo.avatar!!.original.isNotEmpty()){
            setImage(Uri.parse(db.userInfo.avatar!!.original))
            Log.i("MSG", "curr ava " + db.userInfo.avatar!!.original)
        }

    }
    fun btnColorDesign(){
        if(db.clientInfo.buttonColor?.type=="gradient"){
            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                db.clientInfo.buttonColor!!.colors.filter { it.isNotBlank() }.map { Color.parseColor(it) }.toIntArray()
            )
            btnChangePwd.background = gradientDrawable
            btnSave.background = gradientDrawable
        }else{
            btnChangePwd.setBackgroundColor(Color.parseColor(db.clientInfo.buttonColor!!.colors[0]))
            btnSave.setBackgroundColor(Color.parseColor(db.clientInfo.buttonColor!!.colors[0]))
        }
    }
    fun logOut(){
        Toast.makeText(context, "You are Logged Out", Toast.LENGTH_SHORT).show()
        db.haveUserInfo = false
        db.sid=""
        val i: Intent? = requireActivity().getPackageManager()
            .getLaunchIntentForPackage(requireActivity().getPackageName())
        i!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(i)
    }
    private fun imageChooserDialog() {
        val adapter: ArrayAdapter<String> =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1)
        adapter.add("Camera")
        adapter.add("Gallery")
        adapter.add("Saved Avatars")
        AlertDialog.Builder(requireContext())
            .setTitle("Change avatar")
            .setAdapter(adapter) { dialog, which ->
                if (which == 0) {
                    getPermissionsForCamera()
                } else
                    if (which == 1) {
                        pickFromGallery()
                    } else {
                        oldAvatarChooser()
                    }
            }
            .create()
            .show()
    }

    private fun showSavedAvatars(): ArrayList<String> {
        val externalStorage = Environment.getExternalStorageState()
        val out = ArrayList<String>()
        if (externalStorage.equals(Environment.MEDIA_MOUNTED)) {
            val storageDirectory = requireContext().getExternalFilesDir(null).toString()
            val drc = File(storageDirectory)
            val files = drc.listFiles()
            for (file in files) {
                if (file.extension == "jpg") {
                    out.add(file.name)
                }
            }
            if(out.isEmpty()){
                Toast.makeText(context, "You didnt save any avatars", Toast.LENGTH_SHORT).show()
            }
            return out
        } else return out
    }

}
