package com.example.sneakersship.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sneakersship.R
import com.example.sneakersship.common.Utility.Companion.TWO
import com.example.sneakersship.databinding.FragmentHomeBinding
import com.example.sneakersship.listners.ItemClickListner
import com.example.sneakersship.models.ResponseItem
import com.example.sneakersship.ui.sneaker_details.SneakerDetailsFragment


class HomeFragment : Fragment() , ItemClickListner, AdapterView.OnItemSelectedListener {

    private lateinit var homeViewModel: HomeViewModel
    private var mBinding: FragmentHomeBinding? = null
    private val binding get() = mBinding!!
    lateinit var sneakerAdapter: SneakerAdapter
    lateinit var sneakerList: ArrayList<ResponseItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(HomeViewModel::class.java)
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initializeView()
        return root
    }

    /*Initializing Views*/
    private fun initializeView() {
        homeViewModel.retrivedata(requireActivity().applicationContext)
        subScribeSneakerListData()
        subScribeFilteredData()
        mBinding?.searchView?.setOnSearchClickListener {
            mBinding?.tvAppTitle?.visibility = View.INVISIBLE
        }
        mBinding?.searchView?.setOnCloseListener {
            mBinding?.tvAppTitle?.visibility = View.VISIBLE
            false
        }
        mBinding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                homeViewModel.getFilteredData(newText,sneakerList)
                return false
            }
        })
        /*val adapter = ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.gender,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mBinding?.tvSpinner?.adapter = adapter
        mBinding?.tvSpinner?.onItemSelectedListener = this*/
    }

    /*thes method Observe Filtered data based on search*/
    private fun subScribeFilteredData() {
        homeViewModel.filtered.observe(this){
            sneakerAdapter.filterList(it)
        }
    }

    /*this method observes ShoesList Data after Parsing from Json */
    private fun subScribeSneakerListData() {
        homeViewModel.shoeList.observe(this){
            sneakerList = it
            setUpSneakerView(sneakerList)
        }
    }

    /*this method initialize Shoe Recyclerview*/
    private fun setUpSneakerView(sneakerList: ArrayList<ResponseItem>) {
        val layoutManager = GridLayoutManager(activity, TWO)
        mBinding?.rvSneaker?.layoutManager = layoutManager
        sneakerAdapter = SneakerAdapter(sneakerList, requireActivity().applicationContext,this)
        mBinding?.rvSneaker?.adapter = sneakerAdapter
        sneakerAdapter.notifyDataSetChanged()

    }

    /*this method returns lastClick dataItem Position from Recuclerview*/
    override fun getPositionData(position: Int) {
        val sneakerDetailsFragment = SneakerDetailsFragment(sneakerList[position])
        val transaction=requireActivity().supportFragmentManager.beginTransaction()
        transaction.add(R.id.main_Container,sneakerDetailsFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    /*onDestroy fragment*/
    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val text: String = p0?.getItemAtPosition(position).toString()
        homeViewModel.getFilteredGenderData(text,sneakerList)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}
}