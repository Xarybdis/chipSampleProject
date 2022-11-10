package com.example.android_chipsampleapp.ui.standalone_image

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.android_chipsampleapp.R
import com.example.android_chipsampleapp.databinding.FragmentDialogStandaloneImageBinding
import com.squareup.picasso.Picasso

class StandaloneImageDialogFragment : DialogFragment(R.layout.fragment_dialog_standalone_image) {
    private var _binding: FragmentDialogStandaloneImageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentDialogStandaloneImageBinding.inflate(LayoutInflater.from(requireContext()))
        val builder = AlertDialog.Builder(requireContext())

        val breedImageUrl = arguments?.getString("imageUrl") ?: ""

        if (breedImageUrl.isNotEmpty()) {
            Picasso.get().load(breedImageUrl).into(binding.breedImage)
//
//            Picasso.get().load(breedImageUrl).into(object : Target {
//                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
//                    val width = bitmap?.width
//                    val height = bitmap?.height
//
//                    if (height != null && width != null) {
//                        val layoutParams = LinearLayout.LayoutParams(width, height)
//                        binding.breedImage.layoutParams = layoutParams
//                    }
//                    binding.breedImage.setImageBitmap(bitmap)
//                }
//
//                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
//                }
//
//                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
//                }
//            })
        }
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return builder.setView(binding.root).create()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}