package el.dv.fayucafinder.core.view

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import el.dv.fayucafinder.databinding.FayucaFinderActivityLayoutBinding

class FayucaFinderActivity : AppCompatActivity() {

    private lateinit var binding: FayucaFinderActivityLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpView()
        binding = FayucaFinderActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setUpView() {
        this.supportActionBar?.hide()
        window.addFlags(View.SYSTEM_UI_FLAG_LAYOUT_STABLE or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    companion object {
        const val TAG = "FayucaFinderActivity"
    }
}
