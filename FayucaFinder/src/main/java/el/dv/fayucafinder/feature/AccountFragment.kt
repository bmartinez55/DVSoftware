package el.dv.fayucafinder.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import el.dv.fayucafinder.databinding.FullScreenComposeLayoutBinding

/**
 * Fragment that holds one of the features
 */
class AccountFragment : Fragment() {

    private lateinit var binding: FullScreenComposeLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FullScreenComposeLayoutBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = this@AccountFragment
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
