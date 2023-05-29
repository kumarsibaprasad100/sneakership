package com.example.sneakersship.ui.sneaker_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.sneakersship.R
import com.example.sneakersship.database.DataBaseHelper
import com.example.sneakersship.databinding.FragmentSneakerDetailsBinding
import com.example.sneakersship.models.ResponseItem
import com.example.sneakersship.common.Utility
import com.example.sneakersship.common.Utility.Companion.EMPTY
import com.example.sneakersship.common.Utility.Companion.ONE
import com.example.sneakersship.ui.cart.CartFragment
import com.example.sneakersship.ui.sneaker_details.color.ColorAdapter
import com.example.sneakersship.listners.ItemClickListner
import com.example.sneakersship.listners.ItemColorListner
import com.example.sneakersship.ui.sneaker_details.size.SizeAdapter
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 * Use the [SneakerDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SneakerDetailsFragment(val sneakerListData: ResponseItem) : Fragment(), ItemClickListner,
    ItemColorListner {

    private lateinit var dataBaseHelper: DataBaseHelper
    private lateinit var colorAdapter: ColorAdapter
    private lateinit var sneakerViewModel: SneakerDetailsViewModel
    lateinit var sizeAdapter: SizeAdapter
    private var mBinding: FragmentSneakerDetailsBinding? = null
    private val binding get() = mBinding!!
    lateinit var viewPagerAdapter: SneakerViewPagerAdapter
    private var dotscount = Utility.ZERO
    private var size: String = EMPTY
    private var color: String = EMPTY

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sneakerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            SneakerDetailsViewModel::class.java
        )
        mBinding = FragmentSneakerDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        dataBaseHelper = DataBaseHelper(requireActivity().applicationContext)
        initializeView()
        return root
    }

    /*initializing and setting data into views*/
    private fun initializeView() {
        mBinding?.tvProductName?.text =
            "${sneakerListData.name} ${Utility.getYear(sneakerListData.releaseDate)}"
        mBinding?.tvProductDesc?.text = sneakerListData.title
        mBinding?.tvPriceValue?.text = "$ ${sneakerListData.retailPrice}"
        mBinding?.ivBack?.setOnClickListener { requireActivity().onBackPressed() }
        mBinding?.btnAddtoCart?.setOnClickListener { sneakerViewModel.validateFields(size, color) }
        sneakerViewModel.getSizedata()
        sneakerViewModel.getColorData()
        sneakerViewModel.getSneakerImageData(sneakerListData)
        subscribeSizeData()
        subScribeColorData()
        subScribeViewPagerData()
        subScribeValidation()
    }

    /*
    *this function adds valid data to database
    * observe size and color data is valid or not
    */
    private fun subScribeValidation() {
        sneakerViewModel.isValid.observe(this) {
            if (it) {
                var id = "${sneakerListData.id.toString()} ${Utility.getUniqueNo()}"
                val name =
                    "${sneakerListData.name.toString()}  ${Utility.getYear(sneakerListData.releaseDate)}"
                dataBaseHelper.addToCart(
                    name,
                    id,
                    sneakerListData.retailPrice.toString(),
                    size,
                    color,
                    sneakerListData.media?.imageUrl.toString()
                )
                goTocart()
            } else {
                Utility.toast(requireActivity().applicationContext, getString(R.string.selectSize))
            }
        }
    }

    /*this function call after successful addition of data in Cart*/
    private fun goTocart() {
        Utility.toast(requireActivity().applicationContext, getString(R.string.addedToCart))
        mBinding?.btnAddtoCart?.text = getText(R.string.addedInCart)
        mBinding?.btnAddtoCart?.isEnabled = false
    }

    /*
    *observe image data and setup ViewPager for Image sliding
    */
    private fun subScribeViewPagerData() {
        sneakerViewModel.allimageList.observe(this) {
            viewPagerAdapter = SneakerViewPagerAdapter(requireActivity().applicationContext, it)
            mBinding?.viewpager?.adapter = viewPagerAdapter
            dotscount = viewPagerAdapter.count
            val newDotsCount = if (dotscount == ONE) Utility.ZERO else dotscount
            val dots = arrayOfNulls<ImageView>(newDotsCount)

            for (i in Utility.ZERO until dotscount) {
                dots[i] = ImageView(requireActivity().applicationContext)
                dots[i]?.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity().applicationContext,
                        R.drawable.active_line
                    )
                )
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(8, Utility.ZERO, 8, Utility.ZERO)
                mBinding?.sliderDots?.addView(dots[i], params)
            }

            dots[Utility.ZERO]?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireActivity().applicationContext,
                    R.drawable.non_active_line
                )
            )

            mBinding?.viewpager?.addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    for (i in Utility.ZERO until dotscount) {
                        dots[i]?.setImageDrawable(
                            ContextCompat.getDrawable(
                                requireActivity().applicationContext, R.drawable.active_line
                            )
                        )
                    }
                    dots[position]?.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireActivity().applicationContext, R.drawable.non_active_line
                        )
                    )
                }

                override fun onPageScrollStateChanged(state: Int) {}
            })

            mBinding?.ivLeft?.setOnClickListener {
                var page: Int = mBinding?.viewpager!!.currentItem
                if (page > Utility.ZERO) {
                    page--
                    mBinding?.viewpager!!.setCurrentItem(page)
                } else if (page == Utility.ZERO) {
                    mBinding?.viewpager!!.setCurrentItem(page)
                }
            }

            mBinding?.ivRight?.setOnClickListener {
                var page: Int = mBinding?.viewpager!!.getCurrentItem()
                page++
                mBinding?.viewpager!!.setCurrentItem(page)
            }
        }
    }

    /* observe ShoeColor data and setup RecyclerView for Color Selection
     */
    private fun subScribeColorData() {
        sneakerViewModel.colorList.observe(this) {
            val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            mBinding?.rvColor?.layoutManager = layoutManager
            colorAdapter = ColorAdapter(it, requireActivity().applicationContext, this)
            mBinding?.rvColor?.adapter = colorAdapter
            colorAdapter.notifyDataSetChanged()
        }
    }

    /*observe ShoeSize data and setup RecyclerView for Size Selection */
    private fun subscribeSizeData() {
        sneakerViewModel.allsizeList?.observe(this) {
            val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            mBinding?.rvSize?.layoutManager = layoutManager
            sizeAdapter = SizeAdapter(it, requireActivity().applicationContext, this)
            mBinding?.rvSize?.adapter = sizeAdapter
            sizeAdapter.notifyDataSetChanged()
        }
    }

    /*this function return selected size*/
    override fun getPositionData(it: Int) {
        mBinding?.btnAddtoCart?.isEnabled = true
        mBinding?.btnAddtoCart?.text = getText(R.string.add_to_cart)
        size = it.toString()
    }

    /*this function return selected color*/
    override fun getColorData(it: String) {
        mBinding?.btnAddtoCart?.isEnabled = true
        mBinding?.btnAddtoCart?.text = getText(R.string.add_to_cart)
        color = it
    }

}