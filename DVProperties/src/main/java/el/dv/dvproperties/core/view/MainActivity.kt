package el.dv.dvproperties.core.view

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import el.dv.dvproperties.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpView()
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setUpView() {
        this.supportActionBar?.hide()
        window.addFlags(View.SYSTEM_UI_FLAG_LAYOUT_STABLE or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
}
