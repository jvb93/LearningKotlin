package com.example.jvbtestapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jvbtestapp.databinding.FragmentFirstBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.await


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val retrofit = ApiClient.getClient()
    val postsApi = retrofit.create(PostsApi::class.java)
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            buttonFirstClicked(view);
        }



        var posts = mutableListOf<Post>();
        posts.add(Post(1, 1, "hello", "content"))
        posts.add(Post(2, 1, "hello2", "content"))
        val recyclerview = binding.postsList;
        val llm = LinearLayoutManager(this.context)
        llm.setOrientation(LinearLayoutManager.VERTICAL)
        recyclerview.setLayoutManager(llm)
        val adapter = PostsListAdapter(posts)
        recyclerview.adapter = adapter

        GlobalScope.launch(Dispatchers.IO) {
            posts.clear()
            posts.addAll(postsApi.getPosts().await());

            this@FirstFragment.activity!!.runOnUiThread(java.lang.Runnable {
                adapter.notifyDataSetChanged()
            })

        }


    }

    private fun buttonFirstClicked(view: View)
    {
        GlobalScope.launch(Dispatchers.IO) {
            var posts = postsApi.getPosts().await();
            Snackbar.make(view, posts.first().title, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}