package el.dv.fayucafinder.core.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import el.dv.fayucafinder.databinding.FayucaFinderActivityLayoutBinding

class FayucaFinderActivity : AppCompatActivity() {

    private lateinit var binding: FayucaFinderActivityLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FayucaFinderActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    companion object {
        const val TAG = "FayucaFinderActivity"
    }
}
