#Please note this is not a shell script file, I have added so that it indents properly.

#Installation Guide - TensorFlow 7.1 on AWS.
# 1. Select Ubuntu 14.04 AMI.
# 2. Select GPU instance - g2.2xlarge (Note: Its a paid instance).
# 3. Please update the storage from 8 to minimum 20 GB.
# 4. Select key value pair and launch.
# 5. ssh in the EC2 instance.

# Installing the basic tools:
sudo apt-get update
sudo apt-get install -y build-essential git python-pip libfreetype6-dev libxft-dev libncurses-dev libopenblas-dev  gfortran python-matplotlib libblas-dev liblapack-dev libatlas-base-dev python-dev python-pydot linux-headers-generic linux-image-extra-virtual unzip python-numpy swig python-pandas python-sklearn unzip
sudo pip install -U pip

# Install CUDA 7.0:
wget http://developer.download.nvidia.com/compute/cuda/repos/ubuntu1410/x86_64/cuda-repo-ubuntu1410_7.0-28_amd64.deb
sudo dpkg -i cuda-repo-ubuntu1410_7.0-28_amd64.deb && rm cuda-repo-ubuntu1410_7.0-28_amd64.deb
sudo apt-get update
sudo apt-get install -y cuda

#It will tell you to reboot but we'll do that later.

# Install cuDNN 6.5 v2 (Note: Requires registration)
# Download link: https://developer.nvidia.com/rdp/cudnn-archive
# wget or scp to get it on the instance
tar -zxf cudnn-6.5-linux-x64-v2.tgz && rm cudnn-6.5-linux-x64-v2.tgz
sudo cp -R cudnn-6.5-linux-x64-v2/lib* /usr/local/cuda/lib64/
sudo cp cudnn-6.5-linux-x64-v2/cudnn.h /usr/local/cuda/include/

# Reboot
sudo reboot

# Export environment variables 
# Note: Each time you get logged out or restart instance, you'll have to export the variables.
# To save time you can just add it to the bashrc file.
export CUDA_HOME=/usr/local/cuda
export CUDA_ROOT=$CUDA_HOME
export PATH=$PATH:$CUDA_ROOT/bin
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$CUDA_ROOT/lib64

# Install TensorFlow dependencies (JDK and BAZEL-0.1.4):
sudo add-apt-repository -y ppa:webupd8team/java
sudo apt-get update
sudo apt-get install -y oracle-java8-installer
wget https://github.com/bazelbuild/bazel/releases/download/0.1.4/bazel-0.1.4-installer-linux-x86_64.sh
chmod a+x bazel-0.1.4-installer-linux-x86_64.sh
sudo ./bazel-0.1.4-installer-linux-x86_64.sh 
rm bazel-0.1.4-installer-linux-x86_64.sh

#Install TensorFlow from Sources:
git clone --recurse-submodules https://github.com/tensorflow/tensorflow

cd tensorflow

# To build with GPU support we have to use cuda 3.9 version.
# It will be generally the last configuration question.
TF_UNOFFICIAL_SETTING=1 ./configure
sudo pip install --upgrade https://storage.googleapis.com/tensorflow/linux/cpu/tensorflow-0.7.1-cp27-none-linux_x86_64.whl
# These three commands are time consuming
bazel build -c opt --config=cuda //tensorflow/cc:tutorials_example_trainer
bazel build -c opt --config=cuda //tensorflow/tools/pip_package:build_pip_package
bazel-bin/tensorflow/tools/pip_package/build_pip_package /tmp/tensorflow_pkg

# Test Installation
cd
python
>>> import tensorflow as tf

# Cheers!!!
# Its just getting started...go back to work!
