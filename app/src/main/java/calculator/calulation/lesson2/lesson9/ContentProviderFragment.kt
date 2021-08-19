package calculator.calulation.lesson2.lesson9

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.fragment.app.Fragment
import calculator.calulation.lesson2.databinding.FragmentContentProviderBinding
import calculator.calulation.lesson2.databinding.FragmentMainBinding

class ContentProviderFragment : Fragment() {

    private var _binding: FragmentContentProviderBinding? = null
    private val binding: FragmentContentProviderBinding
        get() :FragmentContentProviderBinding {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentProviderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
    }

    private fun checkPermission() {
        context?.let {
            if (ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.READ_CONTACTS
                ) == PERMISSION_GRANTED
            ) {
                Log.d("mylogs", "")
                getContacts()
            } else {

                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                    // нужна рационализация
                    showRatio(it)
                } else {
                    requestPermission()
                    Log.d("mylogs", "")
                }

            }
        }
    }

    private fun showRatio(it: Context) {
        AlertDialog.Builder(it)
            .setTitle("Требуется доступ к контактам")
            .setMessage("Без этого разрешение приложение работать не будет")
            .setPositiveButton("Пускай работает корректно") { _, _ ->
                requestPermission()
            }
            .setNegativeButton("Пускай не работает") { dialog, _ ->
                dialog.dismiss()
                requireActivity().finish()
            }
            .create()
            .show()
    }

    private val REQUEST_CODE_FOR_CONTACTS = 1
    private val REQUEST_CODE_FOR_CAMERA = 2
    private fun requestPermission() {
        requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CODE_FOR_CONTACTS) //FIXME
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_CODE_FOR_CONTACTS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                    getContacts()
                } else {
                    context?.let { showRatio(it) }
                }
            }
        }
    }

    private fun getContacts() {
        context?.let { context: Context ->
            val contentResolver = context.contentResolver
            val cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            )

            cursor?.let { cursor: Cursor ->
                for (i in 0..cursor.count) {
                    if (cursor.moveToPosition(i)) {
                        val name =cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                        Toast.makeText(context, "Контакт $name", Toast.LENGTH_SHORT).show()
                        binding.containerForContacts.addView(TextView(context).apply {
                            text =name
                            textSize = 30f // FIXME
                        })
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = ContentProviderFragment()
    }

}